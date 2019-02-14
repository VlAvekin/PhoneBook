<#import "parts/common.ftl" as common>
<#include "parts/security.ftl">
<#import "parts/phoneBookEdit.ftl" as pb>
<@common.page>

    <@pb.book false/>

    <#include "parts/phoneBookList.ftl">

</@common.page>