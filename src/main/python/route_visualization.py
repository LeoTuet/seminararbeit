import json
import osmnx as ox
import osm_loader
import sys


# copies from osmnx plot.py
def plot_route(route: list[int], route_color: str, line_width: int):

    # assemble the route edge geometries' x and y coords then plot the line
    x = []
    y = []
    for u, v in zip(route[:-1], route[1:]):
        # if there are parallel edges, select the shortest in length
        data = min(graph.get_edge_data(u, v).values(), key=lambda d: d["length"])
        if "geometry" in data:
            # if geometry attribute exists, add all its coords to list
            xs, ys = data["geometry"].xy
            x.extend(xs)
            y.extend(ys)
        else:
            # otherwise, the edge is a straight line from node to node
            x.extend((graph.nodes[u]["x"], graph.nodes[v]["x"]))
            y.extend((graph.nodes[u]["y"], graph.nodes[v]["y"]))
    ax.plot(x, y, c=route_color, lw=line_width)


graph = osm_loader.load_or_get_osm(sys.argv[1], sys.argv[2])
file_name = osm_loader.get_file_name(sys.argv[1], sys.argv[2]) + "-route"

line_width = 0.1
path_line_width = 0.2
route_line_width = 1
if int(sys.argv[1]) <= 10000:
    line_width = 1
    path_line_width = 1.2
    route_line_width = 1.4

route_path = "./src/main/resources/route.json"
route = json.load(open(route_path))

plot_paths = sys.argv[3] if 3 < len(sys.argv) else None

if plot_paths:
    file_name = file_name + "-and-paths"
    paths_path = "./src/main/resources/paths.json"
    paths = json.load(open(paths_path))


bgcolor = "white"
edge_color = "black"


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

if plot_paths:
    for path in paths:
        plot_route(path, "#2f9e44", path_line_width)

plot_route(route, "#e03131", route_line_width)


# save and show the figure as specified, passing relevant kwargs
fig, ax = ox.plot._save_and_show(
    fig,
    ax,
    save=True,
    show=False,
    filepath="./src/main/resources/" + file_name + ".png",
    dpi=1200,
)
