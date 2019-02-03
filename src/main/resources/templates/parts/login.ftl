<#macro login path isRegisterForm>
     <form action="${path}" method="post" class="mt-3">

        <#if isRegisterForm>
        <div class="form-group row">
            <label class="col-sm-1 col-form-label">Full Name:</label>
            <div class="col-sm-4">
                <input class="form-control ${(fullNameError??)?string('is-invalid','')}"
                    type="text" name="fullName" value="<#if user??>${user.fullName}</#if>"
                    onkeyup=" return validateTextCol(this, 5); "/>
                <#if fullNameError??>
                    <div class="invalid-feedback">
                    ${fullNameError}
                    </div>
                </#if>
            </div>
        </div>
        </#if>

        <div class="form-group row">
            <label class="col-sm-1 col-form-label">Login:</label>
            <div class="col-sm-4">
                <input class="form-control ${(usernameError??)?string('is-invalid','')}"
                    type="text" name="username" value="<#if user??>${user.username}</#if>"
                    onkeyup=" return validateTextCol(this, 3, 'en'); "/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                    ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label for="inputPassword" class="col-sm-1 col-form-label">Password:</label>
            <div class="col-sm-4">
                <input class="form-control ${(passwordError??)?string('is-invalid','')}"
                    id="inputPassword" type="password" name="password"
                    onkeyup=" return validateTextCol(this, 5); "/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                    ${passwordError}
                    </div>
                </#if>
            </div>
        </div>

        <#if isRegisterForm>
            <div class="form-group row">
            <label for="inputPassword" class="col-sm-1 col-form-label">Password:</label>
            <div class="col-sm-4">
            <input class="form-control ${(password2Error??)?string('is-invalid','')}"
            id="inputPassword" type="password" name="password2"
            onkeyup=" return validateTextCol(this, 5); "/>
            <#if password2Error??>
                <div class="invalid-feedback">
                ${password2Error}
                </div>
            </#if>
            </div>
            </div>
        </#if>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <div class="form-group">
            <button type="submit" class="btn btn-primary ml-2">
             <#if isRegisterForm>Create <#else>Sign In </#if>
            </button>
        </div>

    </form>
</#macro>

<#macro logout isForm>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary">
        <#if isForm>Sign Out <#else>Sign In </#if>
        </button>
    </form>
</#macro>