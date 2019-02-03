<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    fullName =  user.getFullName()
    currentUserId = user.getId()
    >
<#else>
    <#assign
    name = "unknown"
    fullName = "quest"
    currentUserId = -1
    >
</#if>