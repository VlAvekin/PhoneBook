function validatephone(phone) {

    phone.value = phone.value.replace(/[^0-9|+|(|)]/g,'');
    $(phone).val(phone.value);
    if(!phone.value.match(/^((\+380)+(50|63|66|67|68|73|91|92|93|94|95|96|97|98|99)+([0-9]){7})$/) )
    {
        $(phone).css({'background':'#FFEDEF' , 'border':'solid 1px red'});
        return false;
    } else if (phone.value == ''){
        $(phone).css({'background':'#FFFFFF' , 'border':'solid 1px #ced4da'});
        return false;
    } else {
        $(phone).css({'background':'#99FF99' , 'border':'solid 1px #99FF99'});
        return true;
    }
}

function validateTextCol(type, col, en) {

    if(en == 'en'){
        type.value = type.value.replace(/[^a-z]/g,'');
    }

    if( (type.value.length + 1) > col) {
        $(type).css({'background':'#99FF99' , 'border':'solid 1px #99FF99'});
        return true;
    }else if (type.value == ''){
        $(type).css({'background':'#FFFFFF' , 'border':'solid 1px #ced4da'});
        return false;
    } else {
        $(type).css({'background':'#FFEDEF' , 'border':'solid 1px red'});
        return false;
    }
}