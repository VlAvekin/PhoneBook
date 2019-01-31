<#import "parts/common.ftl" as common>

<@common.page>

    <div>
        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <input type="submit" value="Sign Out"/>
        </form>
    </div>

    <form method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />

        <div class="form-row">
            <div class="form-group col-md-4">
                <input class="form-control" type="text" name="lastName" placeholder="Last Name"/>
            </div>
            <div class="form-group col-md-4">
                <input class="form-control" type="text" name="name" placeholder="Name"/>
            </div>
            <div class="form-group col-md-4">
                <input class="form-control" type="text" name="patronymic" placeholder="Patronymic"/>
            </div>
        </div>

        <div class="form-row">
            <div class="form-group col-md-6">
                <input class="form-control" type="text" name="mobilePhone" placeholder="Mobile Phone"/>
            </div>
            <div class="form-group col-md-6">
                <input class="form-control" type="text" name="homePhone" placeholder="Home Phone"/>
            </div>
        </div>

        <div class="form-group">
            <input class="form-control" type="text" name="address" placeholder="Address"/>
        </div>
        <div class="form-group">
            <input class="form-control" id="inputEmail4" type="text" name="email" placeholder="Email"/>
        </div>

        <button type="submit" class="btn btn-primary ml-2">Submit</button>
    </form>

    <form method="get" action="/phoneBook" class="mt-5">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <div class="form-row">
            <div class="form-group col-md-6">
                <input class="form-control" type="text" name="search" value="" placeholder="Search" />
            </div>
            <div class="form-group col-md-6">
                <button type="submit" class="btn btn-primary ml-2">Search</button>
            </div>
        </div>
    </form>

    <h3 class="text-center mt-2">Phone Book</h3>
    <table class="table table-striped">
        <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Last Name</th>
                <th scope="col">Name</th>
                <th scope="col">Patronymic</th>
                <th scope="col">Mobile Phone</th>
                <th scope="col">Home Phone</th>
                <th scope="col">Address</th>
                <th scope="col">Email</th>
                <th scope="col">Author</th>
                <th scope="col"></th>
            </tr>
        </thead>
        <tbody>
        <#list phoneBooks as phoneBook>
            <tr>
            <td scope="row">${phoneBook.id}</td>
            <td>${phoneBook.lastName}</td>
            <td>${phoneBook.name}</td>
            <td>${phoneBook.patronymic}</td>
            <td>${phoneBook.mobilePhone}</td>
            <td>${phoneBook.homePhone}</td>
            <td>${phoneBook.address}</td>
            <td>${phoneBook.email}</td>
            <td>${phoneBook.author.username}</td>
            <td>
                <div class="form-row">
                    <#--<form method="get" action="/phoneBook/${phoneBook.id}">-->
                        <#--<input type="hidden" name="_csrf" value="${_csrf.token}" />-->
                        <button type="button" class="btn btn-primary ml-2 btn-sm">Edit</button>
                    <#--</form>-->
                    <form method="post" action="/phoneBook/${phoneBook.id}">
                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                        <button type="submit" class="btn btn-primary ml-2 btn-sm">X</button>
                    </form>
                </div>
            </td>
            </tr>
        </#list>
        </tbody>
    </table>

</@common.page>