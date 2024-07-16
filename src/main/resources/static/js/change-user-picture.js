function previewUserPicture(input) {
    const file = input.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            document.getElementById('userPicturePreview').src = e.target.result;
            document.querySelectorAll('.fa-camera').forEach(function (icon) {
                icon.style.display = 'none';
            });
            document.querySelectorAll('.user-picture').forEach(function (img) {
                img.style.display = 'inline-block';
            });
            document.querySelectorAll('.picture').forEach(function (picture) {
                picture.style.padding = '0.5rem';
            });
        };
        reader.readAsDataURL(file);
    }
}