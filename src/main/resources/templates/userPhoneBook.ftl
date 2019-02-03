<#import "parts/common.ftl" as common>
<#include "parts/security.ftl">

<@common.page>

    <#if isCurrentUser>
        <#include "parts/phoneBookEdit.ftl">
    </#if>

    <#include "parts/phoneBookList.ftl">

</@common.page>