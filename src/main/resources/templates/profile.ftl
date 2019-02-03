<#import "parts/common.ftl" as common>

<@common.page>
    <div class="container mt-5">
        <div class="form-group row">
                <label class="col-sm-1 col-form-label">My login:</label>
                <label class="col-sm-1 col-form-label"><b>${username!''}</b></label>
        </div>

        <form method="post" class="mt-4">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="username" value="${username}"/>

            <div class="form-group row">
                <label class="col-sm-1 col-form-label">Full Name:</label>
                <div class="col-sm-4">
                    <input name="fullName" class="form-control  ${(fullNameError??)?string('is-invalid','')}"
                    value="${fullName!''}"  onkeyup=" return validateTextCol(this, 5);"/>
                    <#if fullNameError??>
                        <div class="invalid-feedback">
                        ${fullNameError}
                        </div>
                    </#if>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-1 col-form-label">Password:</label>
                <div class="col-sm-4">
                    <input type="password" name="password"
                           class="form-control ${(passwordError??)?string('is-invalid','')}"
                           onkeyup=" return validateTextCol(this, 5);"/>
                        <#if passwordError??>
                            <div class="invalid-feedback">
                            ${passwordError}
                            </div>
                        </#if>
                </div>
            </div>

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

            <div class="form-group row">
                <button type="submit" class="btn btn-primary">
                    Save
                </button>
            </div>
        </form>
    </div>
</@common.page>