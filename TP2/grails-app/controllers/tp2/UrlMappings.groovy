package tp2


class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{

        }

        "/library/$id1?/book/$id2?(.$format)?" (controller: "api",action:"bookInLibrary")



        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')

    }
}
