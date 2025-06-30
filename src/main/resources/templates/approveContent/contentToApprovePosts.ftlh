<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>
    <div class="container-fluid px-4">
        <h1 class="mt-4">Posts Pending Approval</h1>

        <div class="card mb-4">
            <div class="card-header">
                <i class="fas fa-table me-1"></i>
                Posts List
                <div class="float-end">
                    <form class="d-flex" action="/admin/contentToApprovePosts" method="get">
                        <input class="form-control me-2" type="search" name="query" placeholder="Search" value="${query!''}">
                        <button class="btn btn-outline-success" type="submit">Search</button>
                    </form>
                </div>
            </div>
            <div class="card-body">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>
                            <a href="?page=${page!1}&size=${size!10}&query=${query!''}&sortBy=name&sortDirection=${(sortBy == 'name' && sortDirection == 'asc')?then('desc','asc')}">
                                Title
                                <#if sortBy == 'name'>
                                    <i class="fas fa-sort-${sortDirection}"></i>
                                </#if>
                            </a>
                        </th>
                        <th>Author</th>
                        <th>Created At</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list posts as post>
                        <tr>
                            <td>${post.id}</td>
                            <td>${post.postTranslations[0].title!''}</td>
                            <td>${post.creator.name!''}</td>
                            <td>${post.createdAt!''}</td>
                            <td>
                                    <span class="badge bg-${post.status}">
                                        ${post.status.name}
                                    </span>
                            </td>
                            <td>
                                <a href="/admin/posts/${post.id}/preview" class="btn btn-sm btn-info" target="_blank">
                                    <i class="fas fa-eye"></i> Preview
                                </a>
                                <a href="/admin/posts/${post.id}/approve" class="btn btn-sm btn-success">
                                    <i class="fas fa-check"></i> Approve
                                </a>
                                <a href="/admin/posts/${post.id}/reject" class="btn btn-sm btn-danger">
                                    <i class="fas fa-times"></i> Reject
                                </a>
                            </td>
                        </tr>
                    <#else>
                        <tr>
                            <td colspan="6" class="text-center">No posts found</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>

                <#if posts?has_content>
                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-center">
                            <#if posts.hasPrevious()>
                                <li class="page-item">
                                    <a class="page-link" href="?page=${posts.number}&size=${posts.size}&query=${query!''}&sortBy=${sortBy!''}&sortDirection=${sortDirection!''}" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                            </#if>

                            <#list 1..posts.totalPages as i>
                                <li class="page-item ${(posts.number + 1 == i)?then('active','')}">
                                    <a class="page-link" href="?page=${i}&size=${posts.size}&query=${query!''}&sortBy=${sortBy!''}&sortDirection=${sortDirection!''}">${i}</a>
                                </li>
                            </#list>

                            <#if posts.hasNext()>
                                <li class="page-item">
                                    <a class="page-link" href="?page=${posts.number + 2}&size=${posts.size}&query=${query!''}&sortBy=${sortBy!''}&sortDirection=${sortDirection!''}" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </#if>
                        </ul>
                    </nav>

                    <div class="text-muted">
                        Showing ${posts.number * posts.size + 1} to ${(posts.number * posts.size + posts.size) gt posts.totalElements?then(posts.totalElements, posts.number * posts.size + posts.size)} of ${posts.totalElements} entries
                    </div>
                </#if>
            </div>
        </div>
    </div>
</@main.layout>