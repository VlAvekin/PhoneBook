<#import "parts/common.ftl" as common>
<#import "parts/phoneBookEdit.ftl" as pb>
<@common.page>

    <@pb.book true/>

    <#include "parts/phoneBookList.ftl">
</@common.page>