<#macro login path isRegisterForm>
     <form action="${path}" method="post" role="form" class="mt-3">

        <#if isRegisterForm>
        <div class="form-group row">
            <label class="col-sm-1 col-form-label">Full Name:</label>
            <div class="col-sm-4">
                <input class="form-control" type="text" name="fullName"/>
            </div>
        </div>
        </#if>

        <div class="form-group row">
            <label class="col-sm-1 col-form-label">Login:</label>
            <div class="col-sm-4">
                <input class="form-control" type="text" name="username"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="inputPassword" class="col-sm-1 col-form-label">Password:</label>
            <div class="col-sm-4">
                <input class="form-control" id="inputPassword" type="password" name="password"/>
            </div>
        </div>
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