import osmnx as ox
import networkx as nx
import os
import pickle
import base64

pickle_path = "./src/main/resources/pickle"


def encode_str(place_name: str):
    base64_string_bytes = base64.b64encode(place_name.encode("utf-8"))
    base64_bytes = base64.b64encode(base64_string_bytes)
    return base64_bytes.decode("utf-8")


def get_file_name(dist: int, place_name: str, raw=False):
    place = encode_str(place_name)
    if raw:
        return "graph-raw-" + str(dist) + "-" + place
    return "graph-" + str(dist) + "-" + place


def get_pickle_path(dist: int, place_name: str, raw=False):
    return pickle_path + "/" + get_file_name(dist, place_name, raw) + ".pickle"


def graph_to_pickle(graph: nx.MultiDiGraph, path: str):
    pickle.dump(graph, open(path, "wb"))


def pickle_to_graph(path: str):
    return pickle.load(open(path, "rb"))


def to_clean_graph(graph):
    highway_speeds = {"none": 130, "signals": 130, "walk": 5}

    # just use the default speed of 50 which is allowed in cities in Germany
    def _default_speed(_):
        return 50

    return ox.add_edge_speeds(
        graph, hwy_speeds=highway_speeds, fallback=50, agg=_default_speed
    )


def load_or_get_osm(dist: int, place_name: str):

    if not os.path.exists(pickle_path):
        os.makedirs(pickle_path, False)

    clean_file_path = get_pickle_path(dist, place_name)
    raw_file_path = get_pickle_path(dist, place_name, True)

    if os.path.exists(clean_file_path):
        print("Loads saved cleaned graph: " + clean_file_path)
        return pickle_to_graph(clean_file_path)

    elif os.path.exists(raw_file_path):
        print("Loads and cleans saved raw graph: " + raw_file_path)
        clean_graph = to_clean_graph(pickle_to_graph(raw_file_path))
        graph_to_pickle(clean_graph, clean_file_path)
        return clean_graph

    else:
        print("Downloads new graph")
        raw_graph = ox.graph_from_address(
            place_name, dist=int(dist), network_type="drive"
        )
        graph_to_pickle(raw_graph, raw_file_path)
        clean_graph = to_clean_graph(raw_graph)
        graph_to_pickle(clean_graph, clean_file_path)

        return clean_graph
