const expand_btn = document.querySelector(".expand-btn");

let activeIndex;

expand_btn.addEventListener("click", () => {
  document.body.classList.toggle("collapsed");
});

const current = window.location.href;

const allLinks = document.querySelectorAll(".sidebar-links a");

allLinks.forEach((elem) => {
  elem.addEventListener("click", function () {
    const hrefLinkClick = elem.href;

    allLinks.forEach((link) => {
      if (link.href == hrefLinkClick) {
        link.classList.add("active");
      } else {
        link.classList.remove("active");
      }
    });
  });
});

const searchInput = document.querySelector(".search__wrapper input");

searchInput.addEventListener("focus", (e) => {
  document.body.classList.remove("collapsed");
});

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