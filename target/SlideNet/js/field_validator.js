/*--------------------Phone field creator--------------------*/

/*--------------------IP field creator--------------------*/

window.addEventListener("DOMContentLoaded", function() {
    function setCursorPosition(pos, elem) {
        elem.focus();
        if (elem.setSelectionRange) elem.setSelectionRange(pos, pos);
        else if (elem.createTextRange) {
            var range = elem.createTextRange();
            range.collapse(true);
            range.moveEnd("character", pos);
            range.moveStart("character", pos);
            range.select()
        }
    }

    function Phonemask(event) {
        var matrix = "+38 (___) ___ ____",
            i = 0,
            def = matrix.replace(/\D/g, ""),
            val = this.value.replace(/\D/g, "");
        if (def.length >= val.length) val = def;
        this.value = matrix.replace(/./g, function(a) {
            return /[_\d]/.test(a) && i < val.length ? val.charAt(i++) : i >= val.length ? "" : a
        });
        if (event.type == "blur") {
            if (this.value.length == 3) this.value = ""
        } else setCursorPosition(this.value.length, this)
    };
    let input = document.querySelector("#phone");
    if (input) {
        input.addEventListener("input", Phonemask, false);
        input.addEventListener("focus", Phonemask, false);
        input.addEventListener("blur", Phonemask, false);
    }

    function IPmask(event) {
        var matrix = "___.___.___.___",
            i = 0,
            def = matrix.replace(/\D/g, ""),
            val = this.value.replace(/\D/g, "");
        if (def.length >= val.length) val = def;
        this.value = matrix.replace(/./g, function(a) {
            return /[_\d]/.test(a) && i < val.length ? val.charAt(i++) : i >= val.length ? "" : a
        });
        if (event.type == "blur") {
            if (this.value.length == 0) this.value = ""
        } else setCursorPosition(this.value.length, this)
    };
    input = document.querySelector("#ip");
    if (input) {
        input.addEventListener("input", IPmask, false);
        input.addEventListener("focus", IPmask, false);
        input.addEventListener("blur", IPmask, false);
    }

    if (document.getElementById("submit_button")) {
        document.getElementById("submit_button").addEventListener("click", checkAll, false);
    }
    if (document.getElementById("change_password_form")) {
        document.getElementById("change_password_form").addEventListener("click", checkPasswordChange, false);
    }
    if (document.getElementById("topup_form")) {
        document.getElementById("topup_form").addEventListener("click", chectTopUpValue, false);
    }
});


/*--------------------Init function--------------------*/
function init() {
    let inputs = document.querySelectorAll("input[data-rule]");

    for (let input of inputs) {
        input.addEventListener('blur', function (){
            let rule = this.dataset.rule;
            let value = this.value;
            let check;
            switch (rule) {
                case "text":
                    check = checkIsText(value);
                    break;
                case "number":
                    check = checkIsNumber(value);
                    break;
                case "phone":
                    check = checkIsPhoneMobile(value);
                    break;
                case "ip":
                    check = checkIsIP(value);
                    break;
                default:
                    alert("invalid rule!");
                    break;
            }

            this.classList.remove('invalid');
            this.classList.remove('valid');
            if (check) {
                this.classList.add('valid');
            } else {
                this.classList.add('invalid');
            }
            return check;
        });
    }
}

//------------Data Validators------------

function checkIsText(data) {
    if (checkIsEmpty(data)) {
        return false;
    }
    let regexp = /[^\(A-Za-zА-Яа-я )\d]/

    return !regexp.test(data);
}

function checkIsNumber(data) {
    if (checkIsEmpty(data)) {
        return false;
    }
    let checkResult;
    let validateRegex = /^\d+$/;
    checkResult = (validateRegex.test(data) && (data.length < 7));

    return checkResult;
}

function checkIsPhoneMobile(data) {
    if (checkIsEmpty(data)) {
        return false;
    }
    return data.length === 18;
}

function checkIsIP(data) {
    if (checkIsEmpty(data)) {
        return false;
    }
    return data.length === 15;
}

function checkIsEmpty(data) {
    return !data;
}

function checkAll() {
    let invalidInputCount = 0;
    let inputs = document.querySelectorAll("input[data-rule]");
    for (let input of inputs) {
        if (input.className.includes("invalid") || !input.value) {
            input.classList.remove('invalid');
            input.classList.remove('valid');
            input.classList.add('invalid');
            invalidInputCount++;
        }
    }
    if (invalidInputCount > 0) {
        event.preventDefault();
        return false;
    }
    return true;
}

//------------Data Validators------------

function checkPasswordChange() {
    let oldPass = document.getElementsByName("oldPass")[0];
    let newPass = document.getElementsByName("newPass")[0];
    let newPassRepeat = document.getElementsByName("newPassRepeat")[0];
    let resultMessage = document.getElementById("change_password_error");
    let error = false;

    if (!oldPass || !newPass || !newPassRepeat) {
        resultMessage.innerHTML = "Please Reload this page without editing html.";
        error = true;
    }

    oldPass.classList.remove('invalid');
    newPass.classList.remove('invalid');
    newPassRepeat.classList.remove('invalid');

    if (!oldPass.value || !newPass.value || !newPassRepeat.value) {
        resultMessage.innerHTML = "Fields cannot be empty!";
        error = true;
    }

    //of course we don't need to check repeated value, but why not? huh)
    if (newPass.value.includes(oldPass.value) || newPassRepeat.value.includes(oldPass.value)) {
        oldPass.classList.add('invalid');
        newPass.classList.add('invalid');
        resultMessage.innerHTML = "The new password repeats the old password";
        error = true;
    }

    if (newPass.value !== newPassRepeat.value) {
        newPass.classList.add('invalid');
        newPassRepeat.classList.add('invalid');
        resultMessage.innerHTML = "Repeated password do not match";
        error = true;
    }

    if (error) {
        event.preventDefault();
    }
}

function chectTopUpValue() {
    let amount = document.getElementsByName("topup_amount")[0];
    let resultMessage = document.getElementById("topup_balance_result");
    let error = false;
    amount.classList.remove('invalid');
    if (!amount) {
        resultMessage.innerHTML = "Please Reload this page without editing html.";
        error = true;
    }
    if (!checkIsNumber(amount.value)) {
        resultMessage.innerHTML = "Amount can't be non-numeric!";
        error = true;
    }
    if (amount.value < 20) {
        resultMessage.innerHTML = "Amount Cant be less than 20!";
        error = true;
    }

    if (error) {
        event.preventDefault();
    }
}






















