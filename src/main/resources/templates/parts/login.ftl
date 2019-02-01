<#macro login path>

        <a href="/registration">Registration</a>

        <form action="/login" method="post" role="form" class="mt-3">
            <div class="form-group row">
                <label class="col-sm-1 col-form-label"> Login : </label>
                <div class="col-sm-4">
                    <input class="form-control" type="text" name="username"/>
                </div>
            </div>
            <div class="form-group row">
                <label for="inputPassword" class="col-sm-1 col-form-label"> Password: </label>
                <div class="col-sm-4">
                    <input class="form-control" id="inputPassword" type="password" name="password"/>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <div class="form-group">
                    <button type="submit" class="btn btn-primary ml-2">Sign In</button>
            </div>
        </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <input class="btn btn-primary" type="submit" value="Sign Out"/>
    </form>
</#macro>