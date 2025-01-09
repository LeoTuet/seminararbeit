colors = [
    "#47cbff",
    "#47c9ff",
    "#47c6ff",
    "#46c4ff",
    "#46c2ff",
    "#46c0ff",
    "#46bdff",
    "#45bbff",
    "#45b9ff",
    "#45b7ff",
    "#45b4ff",
    "#44b2ff",
    "#44b0ff",
    "#44adff",
    "#44abff",
    "#43a9ff",
    "#43a6ff",
    "#43a4ff",
    "#43a2ff",
    "#429fff",
    "#429dff",
    "#429aff",
    "#4298ff",
    "#4196ff",
    "#4193ff",
    "#4191ff",
    "#418fff",
    "#408cff",
    "#408aff",
    "#4087ff",
    "#4085ff",
    "#4083ff",
    "#3f80ff",
    "#3f7eff",
    "#3f7bff",
    "#3f79ff",
    "#3e76ff",
    "#3e74ff",
    "#3e71ff",
    "#3e6fff",
    "#3d6cff",
    "#3d6aff",
    "#3d67ff",
    "#3d65ff",
    "#3c62ff",
    "#3c60ff",
    "#3c5dff",
    "#3c5bff",
    "#3b58ff",
    "#3b56ff",
    "#3b53ff",
    "#3b51ff",
    "#3a4eff",
    "#3a4cff",
    "#3a49ff",
    "#3a47ff",
    "#3944ff",
    "#3941ff",
    "#393fff",
    "#393cff",
    "#393aff",
    "#3938ff",
    "#3c38ff",
    "#3e38ff",
    "#4038ff",
    "#4237ff",
    "#4437ff",
    "#4637ff",
    "#4837ff",
    "#4b36ff",
    "#4d36ff",
    "#4f36ff",
    "#5136ff",
    "#5335ff",
    "#5535ff",
    "#5835ff",
    "#5a35ff",
    "#5c34ff",
    "#5e34ff",
    "#6034ff",
    "#6334ff",
    "#6533ff",
    "#6733ff",
    "#6933ff",
    "#6c33ff",
    "#6e32ff",
    "#7032ff",
    "#7232ff",
    "#7532ff",
    "#7732ff",
    "#7931ff",
    "#7c31ff",
    "#7e31ff",
    "#8031ff",
    "#8230ff",
    "#8530ff",
    "#8730ff",
    "#8930ff",
    "#8c2fff",
    "#8e2fff",
    "#902fff",
    "#932fff",
    "#952eff",
    "#972eff",
    "#9a2eff",
    "#9c2eff",
    "#9e2dff",
    "#a12dff",
    "#a32dff",
    "#a62dff",
    "#a82cff",
    "#aa2cff",
    "#ad2cff",
    "#af2cff",
    "#b22cff",
    "#b42bff",
    "#b72bff",
    "#b92bff",
    "#bb2bff",
    "#be2aff",
    "#c02aff",
    "#c32aff",
    "#c52aff",
    "#c829ff",
    "#ca29ff",
    "#cd29ff",
    "#cf29ff",
    "#d228ff",
    "#d428ff",
    "#d728ff",
    "#d928ff",
    "#dc27ff",
    "#de27ff",
    "#e127ff",
    "#e327ff",
    "#e626ff",
    "#e826ff",
    "#eb26ff",
    "#ed26ff",
    "#f025ff",
    "#f325ff",
    "#f525ff",
    "#f825ff",
    "#fa25ff",
    "#fd24ff",
    "#ff24ff",
    "#ff24fc",
    "#ff24f9",
    "#ff23f7",
    "#ff23f4",
    "#ff23f2",
    "#ff23ef",
    "#ff22ec",
    "#ff22ea",
    "#ff22e7",
    "#ff22e4",
    "#ff21e2",
    "#ff21df",
    "#ff21dc",
    "#ff21da",
    "#ff20d7",
    "#ff20d4",
    "#ff20d2",
    "#ff20cf",
    "#ff1fcc",
    "#ff1fc9",
    "#ff1fc7",
    "#ff1fc4",
    "#ff1ec1",
    "#ff1ebf",
    "#ff1ebc",
    "#ff1eb9",
    "#ff1eb6",
    "#ff1db4",
    "#ff1db1",
    "#ff1dae",
    "#ff1dab",
    "#ff1ca9",
    "#ff1ca6",
    "#ff1ca3",
    "#ff1ca0",
    "#ff1b9d",
    "#ff1b9b",
    "#ff1b98",
    "#ff1b95",
    "#ff1a92",
    "#ff1a8f",
    "#ff1a8c",
    "#ff1a8a",
    "#ff1987",
    "#ff1984",
    "#ff1981",
    "#ff197e",
    "#ff187b",
    "#ff1878",
    "#ff1876",
    "#ff1873",
    "#ff1770",
    "#ff176d",
    "#ff176a",
]


def get_color_from_time(time: float):
    if (time < 0):
        return '#000' 
    calculated_index = round(time * 2000)
    safe_index = max(min(calculated_index, 200), 1)
    return colors[safe_index - 1]
