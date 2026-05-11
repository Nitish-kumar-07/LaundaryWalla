const buttons = document.querySelectorAll(".Add");
const cartList = document.querySelector(".prodeuct-added");
let cartItems = {};
let totalAmount = 0;

const totalDisplay = document.createElement("div");
totalDisplay.classList.add("total-display");
totalDisplay.textContent = "Total: ₹0.00";
cartList.parentElement.appendChild(totalDisplay);

buttons.forEach((button) => {
    button.addEventListener("click", function () {
        const parent = button.closest(".each-service");
        const serviceName = parent.querySelector("p").textContent.trim();
        const servicePriceText = parent.querySelector("span").textContent.trim();
        const itemId = button.id;

        const servicePrice = parseFloat(servicePriceText.replace(/[^\d.]/g, ""));

        if (!cartItems[itemId]) {
            const li = document.createElement("li");
            li.classList.add("added");
            li.setAttribute("data-id", itemId);
            li.innerHTML = `<span>${Object.keys(cartItems).length + 1}</span> <span>${serviceName}</span> <span>${servicePriceText}</span>`;
            cartList.appendChild(li);
            cartItems[itemId] = { li, price: servicePrice };
            totalAmount += servicePrice;
        } else {
            cartList.removeChild(cartItems[itemId].li);
            totalAmount -= cartItems[itemId].price;
            delete cartItems[itemId];

            Array.from(cartList.children).forEach((li, index) => {
                li.querySelector("span").textContent = index + 1;
            });
        }

        totalDisplay.textContent = `Total: ₹${totalAmount.toFixed(2)}`;
    });
});

// Update the submitBooking function to call Spring Boot API
function submitBooking() {
    const fullName = document.getElementById('fullName').value;
    const email = document.getElementById('email').value;
    const phone = document.getElementById('phone').value;

    if (!fullName || !email || !phone) {
        alert('Please fill in all fields');
        return;
    }

    if (phone.length !== 10 || isNaN(phone)) {
        alert('Please enter a valid 10-digit phone number');
        return;
    }

    if (Object.keys(cartItems).length === 0) {
        alert('Please add at least one service to your cart');
        return;
    }

    // Prepare cart data for API
    const cartData = [];
    document.querySelectorAll('.added').forEach((item) => {
        const spans = item.querySelectorAll('span');
        cartData.push({
            service: spans[1].textContent,
            price: spans[2].textContent
        });
    });

    // Prepare request body
    const bookingRequest = {
        fullName: fullName,
        email: email,
        phone: phone,
        totalAmount: totalAmount,
        cartItems: cartData
    };

    // Show loading state
    const bookBtn = document.querySelector('.book-now');
    bookBtn.textContent = 'Processing...';
    bookBtn.disabled = true;

    // Call Spring Boot API
    fetch('http://localhost:8081/api/bookings', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(bookingRequest)
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(err => { throw err; });
        }
        return response.json();
    })
    .then(data => {
        // Show success message with booking details
        alert(`✅ Booking confirmed!\nBooking ID: #LW${String(data.bookingId).padStart(5, '0')}\nTotal: ₹${data.totalAmount}`);
        // Optionally redirect to a success page
        // window.location.href = `/booking-success.html?id=${data.bookingId}`;

        // Reset form and cart
        document.getElementById('fullName').value = '';
        document.getElementById('email').value = '';
        document.getElementById('phone').value = '';
        cartList.innerHTML = '';
        cartItems = {};
        totalAmount = 0;
        totalDisplay.textContent = 'Total: ₹0.00';
    })
    .catch(error => {
        console.error('Error:', error);
        alert('❌ Booking failed: ' + (error.message || 'Please try again'));
    })
    .finally(() => {
        bookBtn.textContent = 'Book now';
        bookBtn.disabled = false;
    });
}

// Update newsletter subscription function
function subscribeNewsletter(event) {
    event.preventDefault();

    const name = document.getElementById('subName').value;
    const email = document.getElementById('subEmail').value;
    const messageDiv = document.getElementById('subscribeMessage');

    const request = {
        fullName: name,
        email: email
    };

    fetch('http://localhost:8081/api/subscribe', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(request)
    })
    .then(response => response.json())
    .then(data => {
        messageDiv.innerHTML = data.message;
        messageDiv.style.color = data.success ? 'green' : 'red';
        if (data.success) {
            document.getElementById('subName').value = '';
            document.getElementById('subEmail').value = '';
        }
    })
    .catch(error => {
        messageDiv.innerHTML = 'Subscription failed';
        messageDiv.style.color = 'red';
    });

    return false;
}