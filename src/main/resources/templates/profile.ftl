<#import "parts/common.ftl" as common>

<@common.page>
    <div class="container mt-5">
    <h5>${username}</h5>

    <form method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password : </label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="Password"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Full Name : </label>
            <div class="col-sm-6">
                <input name="fullName" class="form-control" placeholder="Email"
                value="${fullName!''}"/>
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