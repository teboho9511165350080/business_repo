var canvas = document.getElementById("myChart");
var ctx = canvas.getContext("2d");

var data = {
    labels: [0.05, 0.1, 0.15, 0.2, 0.25, 0.3, 0.35, 0.4, 0.45, 0.5, 0.55, 0.6, 0.65, 0.7, 0.75, 0.8, 0.85, 0.9, 0.95, 1],
    datasets: [
        {
            type: 'line',
            label: 'Basic',
            data: [19304,13433,9341,6931,5169, 3885,2927,2159,1853,1502, 1176,911,724,590,491, 400,335,280,239,200]
        }
    ]
};


var myNewChart = new Chart(ctx , {
    type: "line",
    data: data
});