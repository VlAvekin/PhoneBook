<#import "parts/common.ftl" as common>
<#import "parts/login.ftl" as l>

<@common.page>
    <a href="/registration">Registration</a>
    <@l.login "/login" false/>
</@common.page>