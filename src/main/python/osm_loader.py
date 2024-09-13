import osmnx as ox
import networkx as nx
import os
import pickle

_place_name = "Wochenmarkt, Germering"
_pickle_path = "./src/main/resources/pickle"


def _get_path(dist: int, raw=False):
    if raw:
        return _pickle_path + "/graph-raw-" + str(dist) + ".pickle"
    return _pickle_path + "/graph-" + str(dist) + ".pickle"


def _try_to_find_biggest_int(mixed_list):
    ints = []
    for item in mixed_list:
        try:
            item = _str_speed_to_int(item)
            ints.append(int(item))
        except:
            continue

    if len(ints) == 0:
        raise Exception()
    return max(ints)


def _str_speed_to_int(max_speed):
    if max_speed is None:
        return 50
    elif max_speed == "none" or max_speed == "signals":
        return 130
    elif max_speed == "walk":
        return 5

    return max_speed


def _clean_graph(graph: nx.MultiDiGraph):
    connected_nodes = set()
    not_needed_highways = [
        "cycleway",
        "footway",
        "path",
        "sidewalk",
        "track",
        "bridleway",
        "service",
    ]

    for source, target, data in list(graph.edges(data=True)):
        highway = data["highway"]
        if type(highway) is list and any(way in not_needed_highways for way in highway):
            graph.remove_edge(source, target)
            continue

        if highway in not_needed_highways:
            graph.remove_edge(source, target)
            continue

        max_speed = _str_speed_to_int(data.get("maxspeed"))

        try:
            max_speed = int(max_speed)
        except:
            try:
                max_speed = _try_to_find_biggest_int(list(max_speed))
            except:
                print("could not parse: " + max_speed * " removing edge")
                graph.remove_edge(source, target)
                continue

        for index in range(len(graph[source][target])):
            graph[source][target][index]["maxspeed"] = max_speed

        connected_nodes.add(source)
        connected_nodes.add(target)

    for node, data in list(graph.nodes(data=True)):
        if not node in connected_nodes:
            graph.remove_node(node)

    return graph


def _graph_to_pickle(graph: nx.MultiDiGraph, path: str):
    pickle.dump(graph, open(path, "wb"))


def _pickle_to_graph(path: str):
    return pickle.load(open(path, "rb"))


def load_or_get_osm(dist: int | str):
    dist = int(dist)

    if not os.path.exists(_pickle_path):
        os.makedirs(_pickle_path, False)

    clean_file_path = _get_path(dist)
    raw_file_path = _get_path(dist, True)

    if os.path.exists(clean_file_path):
        return _pickle_to_graph(clean_file_path)

    elif os.path.exists(raw_file_path):
        clean_graph = _clean_graph(_pickle_to_graph(raw_file_path))
        _graph_to_pickle(clean_graph, clean_file_path)
        return clean_graph

    else:
        raw_graph = ox.graph_from_address(_place_name, dist=dist)
        _graph_to_pickle(raw_graph, raw_file_path)
        clean_graph = _clean_graph(raw_graph)
        _graph_to_pickle(clean_graph, clean_file_path)

        return clean_graph
