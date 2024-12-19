let menu = document.querySelector('#menu-bars');
let navbar = document.querySelector('.navbar');

menu.onclick = () =>{
    menu.classList.toggle('fa-times');
    navbar.classList.toggle('active');
}

//fungsi untuk home swiper
var swiper = new Swiper(".home-slider", {
    spaceBetween: 30,
    centeredSlides: true,
    autoplay: {
      delay: 7500,
      disableOnInteraction: false,
    },
    pagination: {
      el: ".swiper-pagination",
      clickable: true,
    },
    loop:true,
  });

  var swiper = new Swiper(".mySwiper", {
    slidesPerView: 1, // Start with one slide on mobile
    spaceBetween: 20, // Space between slides
    loop : true,
    pagination: {
        el: ".swiper-pagination",
        clickable: true,
    },
    breakpoints: {
        // Responsive breakpoints for different screen sizes
        640: {
            slidesPerView: 1, // 1 slide for screens >= 640px
        },
        768: {
            slidesPerView: 2, // 2 slides for screens >= 768px
        },
        1024: {
            slidesPerView: 3, // 3 slides for screens >= 1024px
        },
    },
});

// Validasi reservasi 

// Fungsi untuk menampilkan pesan error
function showError(message) {
  Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: message,
  });
}

// Fungsi untuk memvalidasi nama
function validateName(inputId, errorMessageEmpty, errorMessageInvalid) {
  let name = document.getElementById(inputId).value;

  if (name == "") {
      showError(errorMessageEmpty);
      return false;
  }

  let regex = /^[a-zA-Z ]+$/;
  if (!regex.test(name)) {
      showError(errorMessageInvalid);
      return false;
  }

  return true;
}

// Fungsi untuk memvalidasi nomor telepon
function validateTelp() {
  let telp = document.getElementById('number-input').value;
  telp = telp.replace(/\D/g, '');
  if (telp == "") {
    showError("Number can't be Empty");
    return false;
  }

  let pattern = /^\d{12,13}$/;
  // /^(?(\d{3}))?[- ]?(\d{3})[- ]?(\d{4})$/;
  if (!pattern.test(telp)) {
    showError("Number must be 12 or 13 digits");
    return false;
  }

  return true;
}
// function validateTelp() {
//   let telp = document.getElementById('number-input').value;

//   if (telp == "") {
//       showError("Number can't be Empty");
//       return false;
//   }

//   if (telp.length != 12) {
//       showError("Number must be 12 digits");
//       return false;
//   }

//   return true;
// }

// Fungsi untuk memvalidasi email
function validateEmail() {
  let email = document.getElementById('email-input').value;

  if (email == "") {
      showError("Email can't be Empty");
      return false;
  }

  let regex = /^[a-zA-Z0-9._-]+@gmail\.com$/;
  if (!regex.test(email)) {
      showError("Email input must have @gmail.com");
      return false;
  }

  return true;
}

// Fungsi untuk memvalidasi jumlah orang
function validatePeople() {
  let people = document.getElementById('people-input').value;

  if (people == "") {
      showError("Enter the valid number");
      return false;
  }

  if (isNaN(people)) {
      showError("Must be a Number");
      return false;
  }

  return true;
}

// Fungsi untuk memvalidasi tanggal dan waktu
function validateDateTime() {
  

const datePicker = document.getElementById('datePicker');
const timePicker = document.getElementById('timePicker');

// Set the minimum date to January 1, 2019
datePicker.min = '2019-01-01';

// Set the maximum date to today
const today = new Date().toISOString().split('T')[0];
datePicker.max = today;

// Set the time range from 10:00 to 20:00 (8 PM)
timePicker.min = '10:00';
timePicker.max = '20:00';

  if (!datePicker.value || !timePicker.value) {
    showError('Please select a date and time within the allowed range.');
    return false;
  }
  
  return true;
}
// const datetime = document.querySelector('datetime-input');
// datetime.addEventListener('change', () => {
//     const selectedDateTime = new Date(datetime.value);
//     const minDateTime = new Date('2019-03-01T10:00');
//     const maxDateTime = new Date(); // Current date and time
    
//     if (selectedDateTime < minDateTime || selectedDateTime > maxDateTime) {
//         showError('Please select a date and time within the allowed range.');
//     }
// });

// function validateDateTime() {
//   let datetime = document.getElementById('datetime-input').value;

//   if (datetime == "") {
//       showError("Enter the date and time");
//       return false;
//   }

//   return true;
// }

// Fungsi untuk memvalidasi semua input
function validateForm() {
  const isValidFirst = validateName('first-input', "First name can't be Empty", "First name can't be Number");
  const isValidLast = validateName('last-input', "Last name can't be Empty", "Last name can't be Number");
  const isValidTelp = validateTelp();
  const isValidEmail = validateEmail();
  const isValidPeople = validatePeople();
  const isValidDateTime = validateDateTime();

  return isValidFirst && isValidLast && isValidTelp && isValidEmail && isValidPeople && isValidDateTime;
}

// Tambahkan event listener untuk tombol Kirim
document.getElementById('booknow').addEventListener('click', function(event) {
  event.preventDefault();

  if (validateForm()) {
      // Jika semua input valid, lakukan tindakan yang diinginkan
      Swal.fire({
          icon: 'success',
          title: 'Success!',
          text: 'For More Info Please Contact +62 1188-9763-0091!'
      });

      document.getElementById('datetime-input').value = "";
      document.getElementById('people-input').value = "";
      document.getElementById('number-input').value = "";
      document.getElementById('email-input').value = "";
  } else {
      // Pesan kesalahan ditangani dalam fungsi showError
  }
});
