<#include "security.ftl">

<#macro book isForm>
<div align="center">
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample"
       role="button" aria-expanded="false" aria-controls="collapseExample">
        ${(isForm)?string('Add new entry to phonebook','Edit')}
    </a>
</div>
<div class="collapse <#if phoneBook??>show</#if> mt-3" id="collapseExample">
    <form method="post">

        <div class="form-row">
            <div class="form-group col-md-4">
                <input class="form-control ${(lastNameError??)?string('is-invalid','')}"
                       type="text" name="lastName" placeholder="Last Name"
                       onkeyup=" return validateTextCol(this, 4); "
                       value="<#if phoneBook??>${phoneBook.lastName}</#if>"
                       id="lastName"/>
                <#if lastNameError??>
                    <div class="invalid-feedback" id="lastName">${lastNameError}</div>
                </#if>
            </div>
            <div class="form-group col-md-4">
                <input class="form-control ${(firstNameError??)?string('is-invalid','')}"
                       type="text" name="firstName" placeholder="First Name"
                       onkeyup=" return validateTextCol(this, 4); "
                       value="<#if phoneBook??>${phoneBook.firstName}</#if>"
                       id="firstName"/>
                <#if firstNameError??>
                    <div class="invalid-feedback" id="firstName">${firstNameError}</div>
                </#if>
            </div>
            <div class="form-group col-md-4">
                <input class="form-control ${(patronymicError??)?string('is-invalid','')}"
                       type="text" name="patronymic" placeholder="Patronymic"
                       onkeyup=" return validateTextCol(this, 4); "
                       value="<#if phoneBook??>${phoneBook.patronymic}</#if>"
                       id="patronymic"/>
                <#if patronymicError??>
                    <div class="invalid-feedback" id="patronymic">${patronymicError}</div>
                </#if>
            </div>
        </div>

        <div class="form-row">
            <div class="form-group col-md-6">

                    <input class="form-control bfh-phone ${(mobilePhoneError??)?string('is-invalid','')}"
                           id="phonefield" type="text" name="mobilePhone" placeholder="Mobile Phone"
                           onkeyup=" return validatephone(this); "
                           value="<#if phoneBook??>${phoneBook.mobilePhone}</#if>"/>
                    <#if mobilePhoneError??>
                        <div class="invalid-feedback" id="mobilePhone">${mobilePhoneError}</div>
                    </#if>
            </div>
            <div class="form-group col-md-6">
                <input class="form-control" type="text" name="homePhone" placeholder="Home Phone"
                       value="<#if phoneBook??>${phoneBook.homePhone}</#if>"
                       id="homePhone"/>
            </div>
        </div>

        <div class="form-row">
            <div class="form-group col-md-6">
                <input class="form-control" type="text" name="address" placeholder="Address"
                       value="<#if phoneBook??>${phoneBook.address}</#if>"
                       id="address"/>
            </div>
            <div class="form-group col-md-6">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="inputGroupPrepend3">@</span>
                    </div>
                    <input class="form-control ${(emailError??)?string('is-invalid','')}"
                           id="inputEmail4" type="text" name="email" placeholder="Email"
                           value="<#if phoneBook??>${phoneBook.email}</#if>"/>
                    <#if emailError??>
                        <div class="invalid-feedback" id="email">${emailError}</div>
                    </#if>
                </div>
            </div>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <#if !isForm>
        <input type="hidden" name="id" value="<#if phoneBook??>${phoneBook.id}</#if>"/>
        </#if>
        <div align="center">
        <button type="submit" class="btn btn-primary ml-2">Save</button>
        </div>
    </form>
</div>
</#macro>