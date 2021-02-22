//Account Management

//------------Create Service&Tariff Options In Account Editor------------
function addNewServiceToAccount(services) {
    event.preventDefault();
    //get all services holder
    let service_tariff_holder = document.getElementById("service_tariff_holder");
    //get count of services
    let services_count = service_tariff_holder.childElementCount;
    //create new holder for service and tariff
    let new_holder = document.createElement('li');
    let new_holder_div = document.createElement('div');
    new_holder.setAttribute('id', services_count.toString());
    //add elements to holder
    createService("new service_choice", services, new_holder_div);
    new_holder.appendChild(new_holder_div);
    createRemoveButton(services_count.toString(), new_holder);
    //add holder to holder :D
    service_tariff_holder.appendChild(new_holder);
}

function createService(name, services, place) {
    place.appendChild(createServiceOption(name, services));
    changeTariff(services[0], place);
}

function createTariff(name, tariffs, place) {
    place.appendChild(createTariffOption(name, tariffs));
}

function changeTariff(service, place) {
    if (place.hasChildNodes() ) {
        if(place.childElementCount > 1){

            place.children[1].remove();
        }
    }
    //array in depending on >service<
    let service_tariffs;
    switch (service) {
        case "lol":
            service_tariffs=['lol1', 'lol2', 'lol3'];
            break;
        case "bob":
            service_tariffs=['bob1', 'bob2', 'bob3'];
            break;
        case "log":
            service_tariffs=['log1', 'log2', 'log3'];
            break;
    }
    createTariff((service + "_tariff"), service_tariffs, place);
}

//------------Options tags creation------------
function createServiceOption(name, services) {
    let select_input = document.createElement('select');
    select_input.setAttribute('name', name);
    select_input.setAttribute('class', 'service_chooser')
    select_input.setAttribute('onchange',
        'changeServiceTariff(value, this.parentElement)')
    let options = [];
    for (i = 0; i < services.length; i++) {
        let option = document.createElement('option');
        option.innerHTML = services[i];
        option.setAttribute('value', services[i]);
        options.push(option);
    }

    options[0].setAttribute('selected','selected');

    for (i = 0; i < options.length; i++) {
        select_input.appendChild(options[i]);
    }

    return select_input;
}

function createTariffOption(name, tariff) {
    let select_input = document.createElement('select');
    select_input.setAttribute('name', name);
    select_input.setAttribute('class', 'service_chooser')
    let options = [];
    for (i = 0; i < tariff.length; i++) {
        let option = document.createElement('option');
        option.innerHTML = tariff[i];
        option.setAttribute('value', tariff[i]);
        options.push(option);
    }

    options[0].setAttribute('selected','selected');

    for (i = 0; i < options.length; i++) {
        select_input.appendChild(options[i]);
    }

    return select_input;
}

//------------Create Remove Button for deleting Service&Tariff Options------------
function createRemoveButton(name, place) {
    let remove_button = document.createElement('button');
    remove_button.setAttribute("onclick", "removeServiceTariffFromAccount('"+name+"')");
    remove_button.setAttribute('class', 'remove_button');
    remove_button.innerHTML = "Remove"
    place.appendChild(remove_button);
}

function removeServiceTariffFromAccount(service_id) {
    document.getElementById(service_id).remove();
}

/*---------------------------------------------------------------------------------------*/


function generatePasswordRequest() {
    event.preventDefault();
    try
    {
        var request = new XMLHttpRequest();
        request.open("PUT", "/cabinet/admin/account_mng/account_editor", true);
        request.onreadystatechange = function() {
            if (request.readyState === 4 && request.status === 200) {
                document.getElementById("new_password").value = request.responseText;
            }
        }
        request.send(null);
        return true;
    } catch(err) {
        alert(err.message);
    }
}

//------------Helpers------------




























