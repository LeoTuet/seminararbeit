import osmnx as ox
import osm_loader
import sys

graph = osm_loader.load_or_get_osm(sys.argv[1], sys.argv[2])
file_name = osm_loader.get_file_name(int(sys.argv[1], sys.argv[2]))
dpi = 1000
line_width = 0.1

if int(sys.argv[1]) <= 10000:
    dpi = 300
    line_width = 1
    map_path = "./src/main/resources/map.html"
    gdfs = ox.graph_to_gdfs(graph, nodes=False)
    gdfs.explore().save(map_path)

ox.plot_graph(
    graph,
    bgcolor="white",
    edge_color="black",
    node_color="black",
    node_edgecolor="black",
    node_size=0,
    edge_linewidth=0.1,
    save=True,
    filepath="./src/main/resources/" + file_name + ".png",
    dpi=dpi,
)
