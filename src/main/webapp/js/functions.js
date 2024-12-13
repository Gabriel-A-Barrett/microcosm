/* If the scripts runs before the DOM is fully loaded this prevents */
document.addEventListener('DOMContentLoaded', () => {
    const button = document.querySelector('#openModalBtn');
    const modal = document.getElementById('userForm'); /* Need to look up css element */
    if (button) {
        button.addEventListener('click', () => {
            console.log('Button to open modal clicked!');
            modal.style.display = 'block'
        });
    } else {
        console.error('Element #openModalBtn not found!');
    }
});
