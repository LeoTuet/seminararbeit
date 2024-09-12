import osmnx as ox
import pandas as pd
import networkx as nx
import os

place_name = "Wochenmarkt, Germering"

graph = ox.graph_from_address(place_name, dist=10000)


nodes = [
    pd.DataFrame({"n": "n", "node": node, "x": data["x"], "y": data["y"]}, index=[i])
    for i, (node, data) in enumerate(graph.nodes(data=True))
]
unnecessary_columns = [
    "service",
    "width",
    "junction",
    "lanes",
    "name",
    "geometry",
    "tunnel",
    "osmid",
    "reversed",
    "bridge",
    "ref",
    "access",
]

edges_df = nx.to_pandas_edgelist(graph)

# https://wiki.openstreetmap.org/wiki/Key:highway
edges_df = edges_df[
    ~edges_df["highway"].isin(["cycleway", "footway", "path", "sidewalk", "track"])
]

edges_df = edges_df.drop(columns=unnecessary_columns)
edges_df.insert(0, "type", "e")
edges = []
used_nodes = []

for index, edge in edges_df.iterrows():
    # TODO maybe write as function which removes this from df
    max_speed = edge["maxspeed"]
    try:
        max_speed = int(max_speed)
    except:
        try:
            max_speed = int(list(max_speed)[0])
        except:
            # things like walk, none, signals
            continue
    pass

    if not type(max_speed) is int or max_speed == "":
        continue
    # TODO here

    source = edge["source"]
    target = edge["target"]
    if not edge["oneway"]:
        edges.append(["e", source, target, edge["length"], max_speed])
        edges.append(["e", target, source, edge["length"], max_speed])
    else:
        edges.append(["e", source, target, edge["length"], max_speed])

    used_nodes.append(source)
    used_nodes.append(target)


nodes_df = pd.concat(nodes)
nodes_df = nodes_df[nodes_df["node"].isin(used_nodes)]
node_path = "./src/main/resources/nodes.csv"
nodes_df.to_csv(node_path, header=False, index=False)

edges_df = pd.DataFrame(edges)
edge_path = "./src/main/resources/edges.csv"
edges_df.to_csv(edge_path, header=False, index=False)

node_csv = open(node_path, "r").read()
edge_csv = open(edge_path, "r").read()

graph_path = "./src/main/resources/graph.csv"
if os.path.exists(graph_path):
    os.remove(graph_path)

os.remove(node_path)
os.remove(edge_path)
f = open(graph_path, "a")
f.write(node_csv + edge_csv)
f.close()

# gdfs = osmnx.graph_to_gdfs(graph, nodes=False)
# m = gdfs.explore()
# m.save("./map.html")
