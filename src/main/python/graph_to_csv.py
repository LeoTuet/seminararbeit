import pandas as pd
import networkx as nx
import osm_loader
import os
import sys

graph = osm_loader.load_or_get_osm(int(sys.argv[1]), str(sys.argv[2]))

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
    "maxspeed",
]

edges_df = nx.to_pandas_edgelist(graph)
edges_df = edges_df.drop(columns=unnecessary_columns, errors="ignore")
edges_df.insert(0, "e", "e")
edges_df = edges_df[["e", "source", "target", "length", "speed_kph", "osmid"]]
# there are only integer values for speed limits in Germany
edges_df["speed_kph"] = edges_df["speed_kph"].astype(int)
print("Edge: " + str(len(edges_df.index)))

nodes = []
for i, node in enumerate(graph.nodes()):
    nodes.append(["n", node, graph.nodes[node]["x"], graph.nodes[node]["y"]])

nodes_df = pd.DataFrame(nodes)
node_path = "./src/main/resources/nodes.csv"
nodes_df.to_csv(node_path, header=False, index=False)
print("Node: " + str(len(nodes_df.index)))

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
