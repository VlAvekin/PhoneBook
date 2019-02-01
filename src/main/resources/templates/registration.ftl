<#import "parts/common.ftl" as common>
<#import "parts/login.ftl" as l>

<@common.page>
Add new user

<#if message??>
    ${message}
</#if>
    <@l.login "/registration" true/>
</@common.page>