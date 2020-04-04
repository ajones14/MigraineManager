function init () {

    var ctx = document.getElementById('triggerChart');
    var triggerChart = new Chart(ctx, {
        type: 'horizontalBar',
        data: {
            labels: triggerLabels,
            datasets: [{
                label: '# of Trigger Occurrences',
                data: triggerData,
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
           scales: {
                yAxes: [{
                    categoryPercentage: 0.6,
                    barPercentage: 1.0
                }],
                xAxes: [{
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
//ctx.update();

    var ctx2 = document.getElementById('symptomChart');
    var symptomChart = new Chart(ctx2, {
        type: 'horizontalBar',
        data: {
            labels: symptomLabels,
            datasets: [{
                label: '# of Symptom Occurrences',
                data: symptomData,
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
           scales: {
                yAxes: [{
                    categoryPercentage: 0.6,
                    barPercentage: 1.0
                }],
                xAxes: [{
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
//ctx2.update();

}

window.onload = init;

