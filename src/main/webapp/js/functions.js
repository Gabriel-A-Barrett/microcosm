function menu_open() {
    document.getElementById("sidebar").style.display = "block";
    document.getElementById("overlay").style.display = "block";
  }
  
  function menu_close() {
    document.getElementById("sidebar").style.display = "none";
    document.getElementById("overlay").style.display = "none";
  }

  async function fetchTemperatureData() {
    const response = await fetch('/api/temperature-data');
    return response.json();
}

async function initializeChart() {
    const data = await fetchTemperatureData();

    const ctx = document.getElementById('temperatureChart').getContext('2d');
    temperatureChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: data.timestamps, // Initial X-axis timestamps
            datasets: [{
                label: 'Temperature (°C)',
                data: data.temperatures, // Initial Y-axis data
                borderColor: 'rgba(75, 192, 192, 1)',
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                fill: true
            }]
        },
        options: {
            scales: {
                x: {
                    type: 'time',
                    time: {
                        unit: 'minute'
                    }
                },
                y: {
                    beginAtZero: false
                }
            }
        }
    });
}

async function updateChart() {
    const newData = await fetchTemperatureData();

    // Update chart labels (timestamps) and data (temperatures)
    temperatureChart.data.labels = newData.timestamps;
    temperatureChart.data.datasets[0].data = newData.temperatures;

    // Re-render the chart with new data
    temperatureChart.update();
}

async function startRealTimeUpdates() {
    await initializeChart();

    // Update the chart every 500 seconds
    setInterval(updateChart, 500000);
}