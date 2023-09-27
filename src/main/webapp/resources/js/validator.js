function validarDNIcliente() {
    var dnicliente = document.getElementById("DNICLI").value;
    if (dnicliente.length === 8) {
        PF('btnregistrarcli').enable();
//        alert("DNI COMPLETADO")
    } else {
        PF('btnregistrarcli').disable();
    }
}

function validarDNIempleado() {
    var dniempleado = document.getElementById("DNIEMP").value;
    if (dniempleado.length === 8) {
        PF('btnregistraremp').enable();
//        alert("DNI COMPLETADO")
    } else {
        PF('btnregistraremp').disable();
    }
}

function validarCelularCliente() {
    var celularcliente = document.getElementById("TELCLI").value;
    if (celularcliente.length === 9) {
        PF('btnregistrarcli').enable();
    } else {
        PF('btnregistrarcli').disable();
    }
}

function validarCelularEmpleado() {
    var celularempleado = document.getElementById("TELEMP").value;
    if (celularempleado.length === 9) {
        PF('btnregistraremp').enable();
    } else {
        PF('btnregistraremp').disable();
    }
}

function validarRUC() {
    var ruc = document.getElementById("ruc").value;
    if (ruc.length === 11) {
        PF('btnregistrar').enable();
    } else {
        PF('btnregistrar').disable();
    }
}



function validarRUC() {
    var ruc = document.getElementById("Activos").value;
    if (ruc.length === 11) {
        PF('btnregistrar').enable();
    } else {
        PF('btnregistrar').disable();
    }
}
