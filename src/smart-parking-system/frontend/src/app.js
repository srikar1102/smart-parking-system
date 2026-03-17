const apiUrl = 'http://localhost:8081/api/parking'; // Updated to match backend port

// Function to fetch parking slots
async function fetchParkingSlots() {
    const response = await fetch(`${apiUrl}/slots`);
    const slots = await response.json();
    displayParkingSlots(slots);
}

// Function to display parking slots
function displayParkingSlots(slots) {
    const slotsContainer = document.getElementById('slots-container');
    slotsContainer.innerHTML = '';

    const freeSlots = [];
    slots
        .slice() // clone, do not mutate original
        .sort((a, b) => Number(a.id) - Number(b.id))
        .forEach(slot => {
            if (slot.status === 'free') {
                freeSlots.push(slot.id);
            }

            const slotDetails = (slot.vehicleDetails && slot.vehicleDetails.toString().trim()) ? slot.vehicleDetails : 'n/a';

            const slotElement = document.createElement('div');
        slotElement.className = 'slot';
        slotElement.innerHTML = `
            <p><strong>Slot ID:</strong> ${slot.id}</p>
            <p><strong>Status:</strong> ${slot.status}</p>
            ${slot.status === 'occupied' ? `<p><strong>Vehicle:</strong> ${slotDetails}</p>` : ''}
            ${slot.status === 'free'
                ? `<button onclick="parkVehicle(${slot.id})">Park Vehicle Here</button>`
                : `<button onclick="vehicleExit(${slot.id})">Vehicle Exit</button>`}
        `;
        slotsContainer.appendChild(slotElement);
    });

    populateFreeSlotSelect(freeSlots);
}

function populateFreeSlotSelect(freeSlots) {
    const select = document.getElementById('slotToPark');
    select.innerHTML = '';

    if (freeSlots.length === 0) {
        const option = document.createElement('option');
        option.value = '';
        option.text = 'No free slots available';
        select.appendChild(option);
        select.disabled = true;
        return;
    }

    select.disabled = false;

    const placeholder = document.createElement('option');
    placeholder.value = '';
    placeholder.text = 'Select a free slot';
    placeholder.selected = true;
    placeholder.disabled = true;
    select.appendChild(placeholder);

    freeSlots
        .map(Number)
        .sort((a, b) => a - b)
        .forEach(slotId => {
            const option = document.createElement('option');
            option.value = slotId;
            option.text = slotId;
            select.appendChild(option);
        });
}

function selectSlotToPark(slotId) {
    const messageDiv = document.getElementById('message');
    document.getElementById('slotToPark').value = slotId;
    messageDiv.textContent = `Slot ${slotId} selected. Enter vehicle details and type, then click Park Vehicle.`;
}

// Function to add a parking slot
async function addParkingSlot() {
    const slotId = document.getElementById('slotId').value;
    const messageDiv = document.getElementById('message');
    if (!slotId) {
        messageDiv.textContent = 'Please enter a Slot ID.';
        return;
    }
    const response = await fetch(`${apiUrl}/slots`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ id: slotId, status: 'free' })
    });
    if (response.ok) {
        alert('Slot has been added.');
        fetchParkingSlots();
    } else {
        alert('Failed to add slot. It may already exist.');
    }
}

// Function to park a vehicle
async function parkVehicle(selectedSlotId) {
    const messageDiv = document.getElementById('message');
    const vehicleNumber = document.getElementById('vehicleNumber').value.trim();
    const slotSelect = document.getElementById('slotToPark');
    let slotId = selectedSlotId ? Number(selectedSlotId) : Number(slotSelect.value);

    if (!slotId) {
        // Auto-select first available free slot if user did not choose one.
        for (let opt of slotSelect.options) {
            if (opt.value && opt.value !== '') {
                slotId = Number(opt.value);
                slotSelect.value = slotId;
                messageDiv.textContent = `No slot selected. Auto-choosing slot ${slotId} for parking.`;
                break;
            }
        }
    }

    if (!vehicleNumber) {
        alert('Please enter vehicle number before parking.');
        return;
    }

    if (!slotId) {
        alert('No free slot available to park.');
        return;
    }

    const response = await fetch(`${apiUrl}/park`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ slotId, vehicleNumber })
    });

    const resultText = await response.text();
    let displayText = resultText;

    // If Spring error JSON is returned, try to parse meaningful message.
    if (resultText && resultText.trim().startsWith('{')) {
        try {
            const body = JSON.parse(resultText);
            displayText = body.error || body.message || resultText;
        } catch (e) {
            displayText = resultText;
        }
    }

    if (response.ok) {
        alert(displayText || 'Vehicle parked successfully.');
        fetchParkingSlots();
        document.getElementById('vehicleNumber').value = '';
        document.getElementById('slotToPark').value = '';
        messageDiv.textContent = 'Parked successfully.';
    } else {
        alert(displayText || 'Unexpected error while parking.');
        messageDiv.textContent = displayText || 'Unexpected error while parking.';
    }
}

// Function for vehicle exit (mark slot free)
async function vehicleExit(slotId) {
    const messageDiv = document.getElementById('message');
    if (typeof slotId === 'undefined') {
        slotId = document.getElementById('slotToRemove').value;
    }
    if (!slotId) {
        messageDiv.textContent = 'Please enter a Slot ID.';
        return;
    }
    const response = await fetch(`${apiUrl}/remove/${slotId}`, {
        method: 'DELETE'
    });
    if (response.ok) {
        alert('Vehicle exit completed. Slot status updated to free.');
        fetchParkingSlots();
    } else {
        alert('Failed to exit vehicle. Slot may not exist or already free.');
    }
}

// Function to delete a slot entirely
async function deleteSlot() {
    const slotId = document.getElementById('slotToDelete').value;
    const messageDiv = document.getElementById('message');
    if (!slotId) {
        messageDiv.textContent = 'Please enter a Slot ID to delete.';
        return;
    }
    const response = await fetch(`${apiUrl}/slots/${slotId}`, {
        method: 'DELETE'
    });
    if (response.ok) {
        alert('Slot deleted successfully.');
        fetchParkingSlots();
    } else {
        alert('Failed to delete slot. Slot may not exist.');
    }
}

// Initial fetch of parking slots
fetchParkingSlots();