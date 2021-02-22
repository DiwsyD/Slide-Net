
/*--------------------------------------METHODS--------------------------------------*/

//--------------Add new row to service table
function createFormsToAddNewTariffRow(service_id) {
    let thisServiceTable = document.getElementById("tariff-table");

    let newRow = document.createElement("tr");
    newRow.setAttribute('id', 'change_row');

    let newTd = document.createElement("td");
    newTd.appendChild(createTextForm("tariffName", "Tariff Name", "", 'text'));
    newRow.appendChild(newTd);

    newTd = document.createElement("td");
    newTd.appendChild(createTextForm("description", "Description", "", 'text'));
    newRow.appendChild(newTd);

    newTd = document.createElement("td");
    newTd.appendChild(createTextForm("price", "Price", "", 'number'));
    newRow.appendChild(newTd);

    newTd = document.createElement("td");
    newTd.classList.add("button-column");
    newTd.appendChild(createCancelButton(service_id));
    newRow.appendChild(newTd);

    thisServiceTable
        .childNodes[thisServiceTable.childNodes.length-2]
        .before(newRow);

    displayEditButtons(false);
    displayAddButton(false);
    displaySaveButton(true);
}

//------------Edit service table row
function createFormsToEditTariffRow(tariff_name, tariff_description, tariff_price) {
    let thisTariffRow = document.getElementById(tariff_name);
    thisTariffRow.setAttribute("class", tariff_name);
    thisTariffRow.id = "change_row";

    let tariffRowColumns = thisTariffRow.getElementsByTagName("td");

    for (i = 0; i < 3; i++) {
        tariffRowColumns[i].innerHTML = "";
    }

    tariffRowColumns[0].appendChild(createTextForm("tariffName", "Tariff Name", tariff_name, 'text'));

    tariffRowColumns[1].appendChild(createTextForm("description", "Description", tariff_description, 'text'));

    tariffRowColumns[2].appendChild(createTextForm("price", "Price", tariff_price, 'number'));

    displayCancelButton(true);
}

//------------Remove Tariff------------
function removeTariffRow(tariffName) {
    try
    {
        var params = "?tariffName=" + tariffName;
        var request = new XMLHttpRequest();
        request.open("delete", "/cabinet/admin/service_mng" + params, true);
        request.onreadystatechange = function() {
            if (request.readyState === 4 && request.status === 200) {
                reloadPage();
            }
        }
        request.send(null);
        return true;
    } catch(err) {
        alert(err.message);
    }
}

//------------Edit service table row
function editTariffRow(tariff_name, tariff_description, tariff_price) {
    createFormsToEditTariffRow(tariff_name, tariff_description, tariff_price);

    displayEditButtons(false);
    displayAddButton(false);
    displaySaveButton(true);
}

//------------Cancel Any action on Tariff
function cancelTariff(tariff_name){
    let tariff_row = document.getElementsByClassName(tariff_name)[0];
    reloadPage();
}

//------------Save row changes in service table
function saveChanges(service_id) {
    let newTariffRow = document.getElementById("change_row");
    if (newTariffRow != null) {
        let tariff = [];
        for (i = 0; i < 3; i++) {
            tariff.push(newTariffRow.getElementsByTagName("td")[i].childNodes[0].value)
        }
        newTariffRequest(service_id, tariff);
    }
}

//------------Make request to send changes
function newTariffRequest(service_id, tariff) {
    if (!checkAll())
        return;
    try
    {
        var params = "?service=" + service_id
            + "&tariffName=" + tariff[0]
            + "&tariffDescription=" + tariff[1]
            + "&tariffPrice=" + tariff[2];
        var request = new XMLHttpRequest();
        request.open("POST", "/cabinet/admin/service_mng" + params, true);
        request.onreadystatechange = function() {
            if (request.readyState === 4 && request.status === 200) {
                reloadPage();
            }
        }
        request.send(null);
        return true;
    } catch(err) {
        alert(err.message);
    }
}

/*--------------------------------------BUTTONS--------------------------------------*/
//--------------Display
function displayAddButton(action) {
    let account_adder = document.getElementById("account_adder");
    if (account_adder === null) {
        return;
    }
    if (action) {
        account_adder.style.display = "inline-block";
    } else {
        account_adder.style.display = "none";
    }
}

function displaySaveButton(action) {
    let saveButtonDiv = document.getElementById("save_changes");
    if (saveButtonDiv === null) {
        return;
    }
    if (action) {
        saveButtonDiv.style.display = "inline-block";
    } else {
        saveButtonDiv.style.display = "none";
    }
}

function displayEditButtons(action) {
    let editButtons = document.getElementsByClassName("edit_button");

    if (action) {
        for(edBut of editButtons) {
            edBut.style.display = "inline-block";
        }
    } else {
        for(edBut of editButtons) {
            edBut.style.display = "none";
        }
    }
}

function displayCancelButton(action) {
    let tariff_row = document.getElementById("change_row");
    let cancelButton = tariff_row.getElementsByClassName("cancel_button")[0];
    if (action) {
        cancelButton.style.display = "inline-block";
    } else {
        cancelButton.style.display = "none";
    }
}


//--------------Create
function createCancelButton(tariff) {
    let cancel_button = document.createElement('button');
    cancel_button.setAttribute("class", "cancel_button");
    cancel_button.setAttribute("onclick", "cancelTariff('" + tariff +"')");
    cancel_button.innerHTML = "Cancel";
    return cancel_button;
}

function createTextForm(name, placeholder, value, dataRule) {
    let input_form = document.createElement('input');
    input_form.setAttribute('data-rule', dataRule);
    input_form.setAttribute('type', 'text');
    input_form.setAttribute('name', name);
    input_form.setAttribute('class', 'input_new_account');
    input_form.setAttribute('placeholder', placeholder);
    input_form.setAttribute('value', value);
    return input_form;
}


/*--------------------------------------HELPERS--------------------------------------*/
function reloadPage() {
    document.location.reload();
}
