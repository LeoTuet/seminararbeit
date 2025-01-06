import json
import osmnx as ox
import osm_loader
import sys
import heatmap_gradient

graph = osm_loader.load_or_get_osm(sys.argv[1], sys.argv[2])

heatmap_path = "./src/main/resources/heatmap.json"
data_points = json.load(open(heatmap_path))

output_file_name = osm_loader.get_file_name(sys.argv[1], sys.argv[2]) + "-heatmap"

bgcolor = "white"
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


for data in data_points:
    node, time = data
    ax.scatter(
        x=graph.nodes[node]["x"],
        y=graph.nodes[node]["y"],
        s=20,
        c=heatmap_gradient.get_color_from_time(time),
        alpha=0.7,
        edgecolor="none",
    )

# save and show the figure as specified, passing relevant kwargs
fig, ax = ox.plot._save_and_show(
    fig,
    ax,
    save=True,
    show=False,
    filepath="./src/main/resources/" + output_file_name + ".png",
    dpi=1200,
)
