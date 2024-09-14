import json
import osmnx as ox
import osm_loader
import sys

route_path = "./src/main/resources/route.json"
route = json.load(open(route_path))
route_set = set(route)

graph = osm_loader.load_or_get_osm(sys.argv[1])

for node, data in list(graph.nodes(data=True)):
    if node not in route_set:
        graph.remove_node(node)

map_path = "./src/main/resources/route.html"
gdf = ox.graph_to_gdfs(graph, nodes=False)
gdf.explore().save(map_path)

ox.plot.plot_graph_route(graph, route)
