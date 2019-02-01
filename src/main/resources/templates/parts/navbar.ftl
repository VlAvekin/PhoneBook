<#include "security.ftl">
<#import "login.ftl" as l>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/">Phone book</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
            aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/phoneBook">List phone book</a>
            </li>

            <#if user??>
                <li class="nav-item">
                    <a class="nav-link" href="/profile">Profile <span class="sr-only">(current)</span></a>
                </li>
            </#if>
        </ul>
    </div>

    <#if user??>
        <div class="navbar-text mr-3">${name}</div>
    </#if>

    <@l.logout user??/>
</nav>
