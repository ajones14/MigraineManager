function init () {

    var ctx = document.getElementById('triggerChart');
    var triggerChart = new Chart(ctx, {
        type: 'horizontalBar',
        data: {
            labels: triggerLabels,
            datasets: [{
                label: '# of Trigger Occurrences',
                data: triggerData,
                backgroundColor: 'rgba(54, 162, 235, 0.4)',
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
                backgroundColor: 'rgba(75, 192, 141, 0.4)',
                borderColor: 'rgba(75, 192, 141, 1)',
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

    var ctx3 = document.getElementById('migraineChart');
    var migraineChart = new Chart(ctx3, {
        type: 'horizontalBar',
        data: {
            labels: migraineLabels,
            datasets: [
                          {
                            label: "# of Migraines",
                            backgroundColor: 'rgba(153, 102, 255, 0.4)',
                            borderColor: 'rgba(153, 102, 255, 1)',
                            borderWidth: 1,
                            data: migraineFrequency
                          },
                          {
                            label: "# of Migraine Days",
                            backgroundColor: 'rgba(54, 162, 235, 0.4)',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            borderWidth: 1,
                            data: migraineDaysPerMonth
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
//ctx3.update();
}

window.onload = init;

