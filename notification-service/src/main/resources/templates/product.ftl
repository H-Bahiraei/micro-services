<#import "/spring.ftl" as spring>
<html>
<h2>My products</h2>
<ul>
    <#list products as product>
        <li>$amp{product}</li>
    </#list>
</ul>
<p>
    <a href="/logout">Logout</a>
</p>
</html>