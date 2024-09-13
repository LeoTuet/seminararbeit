import osmnx as ox
import pandas as pd
import networkx as nx
import osm_loader
import os
import sys

graph = osm_loader.load_or_get_osm(sys.argv[1])

unnecessary_columns = [
    "service",
    "width",
    "junction",
    "lanes",
    "name",
    "geometry",
    "tunnel",
    # "osmid",
    "reversed",
    "bridge",
    "ref",
    "access",
]

edges_df = nx.to_pandas_edgelist(graph)
edges_df = edges_df.drop(columns=unnecessary_columns, errors="ignore")
edges_df.insert(0, "e", "e")
edges_df = edges_df[["e", "source", "target", "length", "maxspeed", "osmid"]]

nodes = []
for i, (node, data) in enumerate(graph.nodes(data=True)):
    nodes.append(["n", node, data["x"], data["y"]])

nodes_df = pd.DataFrame(nodes)
node_path = "./src/main/resources/nodes.csv"
nodes_df.to_csv(node_path, header=False, index=False)

edge_path = "./src/main/resources/edges.csv"
edges_df.to_csv(edge_path, header=False, index=False)

node_csv = open(node_path, "r").read()
edge_csv = open(edge_path, "r").read()
os.remove(node_path)
os.remove(edge_path)

graph_path = "./src/main/resources/graph.csv"
if os.path.exists(graph_path):
    os.remove(graph_path)

f = open(graph_path, "a")
f.write(node_csv + edge_csv)
f.close()


# map_path = "./src/main/resources/map.html"
# gdfs = ox.graph_to_gdfs(graph, nodes=False)
# gdfs.explore().save(map_path)
