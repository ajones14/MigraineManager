function init () {

    var ctx = document.getElementById('myChart');
    var myChart = new Chart(ctx, {
        type: 'horizontalBar',
        data: {
            labels: triggerLabels,
            datasets: [{
                label: 'Most Common Triggers',
                data: [12, 19, 3, 5, 6, 3],
                backgroundColor: [
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(54, 162, 235, 0.2)'
                    ],
                borderColor: [
                    'rgba(54, 162, 235, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(54, 162, 235, 1)'
                    ],
                borderWidth: 1
            }]
        },
        options: {
           scales: {
                yAxes: [{
                    categoryPercentage: 0.6,
                    barPercentage: 1.0,
                    ticks: {
                        beginAtZero: true
                    }
                }]
            },
            legend: {
                labels: { fontSize: 18 }
            }
        }
    });
ctx.update();
}

window.onload = init;

