<#include "security.ftl">

<form method="get" class="mt-5">
    <div class="form-row">
        <div class="form-group col-md-6">
            <input class="form-control" type="text" name="search" value="" placeholder="Search"/>
        </div>
        <div class="form-group col-md-6">
            <button type="submit" class="btn btn-primary ml-2">Search</button>
        </div>
    </div>
</form>

<h3 class="text-center mt-2">Phone Book</h3>
<table class="table table-striped">
    <thead class="thead-dark">
    <tr>
        <th scope="col">Last Name</th>
        <th scope="col">First Name</th>
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
        <tr data_id="${phoneBook.id}">
            <td>${phoneBook.lastName}</td>
            <td>${phoneBook.firstName}</td>
            <td>${phoneBook.patronymic}</td>
            <td>${phoneBook.mobilePhone}</td>
            <td>${phoneBook.homePhone!''}</td>
            <td>${phoneBook.address!''}</td>
            <td>${phoneBook.email!''}</td>
            <td>${phoneBook.author.username}</td>
            <td>
                <#if phoneBook.author.id == currentUserId>
                    <div class="form-row">
                        <a href="/user-phone-book/${phoneBook.author.id}?phoneBook=${phoneBook.id}" type="button"
                            class="btn btn-primary ml-2 btn-sm">Edit</a>

                        <form method="post" action="/user-phone-book/${phoneBook.author.id}/${phoneBook.id}">
                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                            <button type="submit" class="btn btn-primary ml-2 btn-sm">X</button>
                        </form>
                    </div>
                </#if>
            </td>
        </tr>
    </#list>
    </tbody>
</table>