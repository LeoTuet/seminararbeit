import osmnx as ox
import copy

place_name = "Wochenmarkt, Germering"


def try_to_find_biggest_int(mixed_list):
    ints = []
    for item in mixed_list:
        try:
            item = str_speed_to_int(item)
            ints.append(int(item))
        except:
            continue

    if len(ints) == 0:
        raise Exception()
    return max(ints)


def str_speed_to_int(max_speed):
    if max_speed is None:
        return 80
    elif max_speed == "none" or max_speed == "signals":
        return 130
    elif max_speed == "walk":
        return 5

    return max_speed


def osm_to_cleaned():
    graph = ox.graph_from_address(place_name, dist=10000)

    edges = copy.deepcopy(graph.edges(data=True))
    connected_nodes = []
    not_needed_highways = [
        "cycleway",
        "footway",
        "path",
        "sidewalk",
        "track",
        "bridleway",
        "service",
    ]

    for source, target, data in edges:
        highway = data["highway"]
        if type(highway) is list and any(way in not_needed_highways for way in highway):
            graph.remove_edge(source, target)
            continue

        if highway in not_needed_highways:
            graph.remove_edge(source, target)
            continue

        max_speed = str_speed_to_int(data.get("maxspeed"))

        try:
            max_speed = int(max_speed)
        except:
            try:
                max_speed = try_to_find_biggest_int(list(max_speed))
            except:
                print("could not parse: " + max_speed * " removing edge")
                graph.remove_edge(source, target)
                continue

        for index in range(len(graph[source][target])):
            graph[source][target][index]["maxspeed"] = max_speed

        connected_nodes.append(source)
        connected_nodes.append(target)

    for node, data in copy.deepcopy(graph.nodes(data=True)):
        if not node in connected_nodes:
            graph.remove_node(node)

    return graph
