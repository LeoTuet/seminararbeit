import json
import osmnx as ox
import osm_loader
import sys
import copy

route_path = "./src/main/resources/route.json"
route = json.load(open(route_path))
route_set = set(route)

graph = osm_loader.load_or_get_osm(sys.argv[1], sys.argv[2])
route_graph = copy.deepcopy(graph)

for node, data in list(route_graph.nodes(data=True)):
    if node not in route_set:
        route_graph.remove_node(node)


bgcolor = "white"
dpi = 1000

edge_color = "black"
line_width = 0.1
fig, ax = ox.plot_graph(
    graph,
    bgcolor=bgcolor,
    edge_color=edge_color,
    node_size=0,
    edge_linewidth=line_width,
    show=False,
    save=False,
    close=False,
)

edge_color = "red"
ox.plot_graph(
    route_graph,
    ax=ax,
    bgcolor=bgcolor,
    edge_color=edge_color,
    node_size=0,
    edge_linewidth=0.2,
    figsize=(8, 8),
)
