document.getElementById('toggleButton').addEventListener('click', function () {
    const currentState = document.getElementById('stateInput').value;
    const newState = (currentState === 'Yes') ? 'No' : 'Yes';

    document.getElementById('stateInput').value = newState;
    document.getElementById('toggleButton').textContent = newState;

    if (newState === 'Yes') {
        document.getElementById('toggleButton').classList.remove('btn-success');
        document.getElementById('toggleButton').classList.add('btn-danger');
    } else {
        document.getElementById('toggleButton').classList.remove('btn-danger');
        document.getElementById('toggleButton').classList.add('btn-success');
    }
});