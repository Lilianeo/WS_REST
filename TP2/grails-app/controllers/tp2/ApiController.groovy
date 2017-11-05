package tp2

import grails.converters.XML
import grails.converters.JSON

class ApiController {

    def index() { }

    def Book ()
    {

        switch(request.getMethod())
        {
            case "POST":
                if(!Library.get(params.library.id))
              {
                  render(status: 404,text: "cannot attach a book to a non existent library(${params.library.id})")
                  return
              }
                def bookInstance=new Book(name: params.name,releaseDate: Date.parse("dd-MM-yyyy", params.releaseDate),isbn: params.isbn,author: params.author,library: Library.findById(params.library.id))//(params)
               if(bookInstance.save(flush: true))
                {
                    response.status=201
                }
                else response.status=400
                break;
            case 'PUT':

                if (params== null) {

                    return
                }
                if(!Book.findById(params.id))
                {
                    render(status: 404,text: "Livre non trouvée")
                }
                def bookInstance =Book.findById(params.id)

                bookInstance.setName(params.name)
                bookInstance.setReleaseDate(Date.parse("dd-MM-yyyy", params.releaseDate))
                bookInstance.setIsbn(params.isbn);
                bookInstance.setAuthor(params.author)
                bookInstance.setLibrary(Library.findById(params.library.id))

                if(bookInstance.save(flush:true))
                {
                    render(status: 200,text: "Livre modifié avec succès")
                }
                else  render(status: 404,text: "Echec de modification du livre")

                break;
            case "GET":

                if(!params.id)
                {
                    switch (request.getHeader("Accept")) {
                        case "application/json":
                            render Book.findAll() as JSON
                            break;

                            case "application/xml":
                            render Book.findAll() as XML
                            break;
                        default:

                            render( status: 405, text:"Format non supporté")

                            break;
                    }


                }
                else
                {
                    if(!Book.get(params.id))response.status=404

             else {
                        switch (request.getHeader("Accept")) {
                            case "application/json":
                                render  Book.findById(params.id) as  JSON
                                break;

                            case "application/xml":
                                render  Book.findById(params.id) as XML
                                break;
                            default:

                                render( status: 405, text:"Format non supporté")

                                break;
                        }
                    }


                }
                break;

            case "DELETE":

                if(!params.id)
                {
                    reponse.status=404
                }

                def book= Book.get(params.id)
                if(!book)
                {
                    reponse.status=404
                }
                else {
                    book.delete(flush:true)
                    response.status=200
                }



                break;
            default:
                response.status= 405
                break;
        }



    }

    def bookInLibrary()
    {

        switch(request.getMethod())
        {
            case "POST":

                if(!Library.findById(params.library.id1))
                {
                    render(status: 404,text: "cannot attach a book to a non existent library(${params.library.id1})")
                    return
                }
                def bookInstance=new Book(name: params.name,releaseDate: Date.parse("dd-MM-yyyy", params.releaseDate),isbn: params.isbn,author: params.author,library: Library.findById(params.library.id1))//(params)
                if(bookInstance.save(flush: true))
                {
                    response.status=201
                }
                else response.status=400




                break;
            case "GET":

                if(!params.id1)
                {
                    if(!params.id2) {
                        switch (request.getHeader("Accept")) {
                            case "application/json":
                                render Book.findAll() as JSON
                                break;

                            case "application/xml":
                                render Book.findAll() as XML
                                break;
                            default:

                                render(status: 405, text: "Format non supporté")

                                break;
                        }
                    }
                    else {
                        if( !Book.findById(params.id2) ) response.status=404
                        else
                        {

                            switch (request.getHeader("Accept")) {
                                case "application/json":
                                    render  Book.findById(params.id2)  as JSON
                                    break;

                                case "application/xml":
                                    render  Book.findById(params.id2) as XML
                                    break;
                                default:

                                    render( status: 405, text:"Format non supporté")

                                    break;
                            }



                        }
                    }


                }
                else
                {
                    if (!params.id2)
                    {

                        def libraryInstance=Library.findById(params.id1)
                            switch (request.getHeader("Accept")) {
                                case "application/json":
                                    render  libraryInstance.getBooks()  as JSON
                                    break;

                                case "application/xml":
                                    render  libraryInstance.getBooks() as XML
                                    break;
                                default:

                                    render( status: 405, text:"Format non supporté")

                                    break;
                            }





                    }
                    else {
                        def libraryInstance=Library.findById(params.id1)
                        switch (request.getHeader("Accept")) {
                            case "application/json":

                                def book=libraryInstance.getBooks().getAt(params.id2)
                                if(!book)
                                {
                                    render(status:404,text:"Livre non trouvée")
                                }
                                else {
                                    render  libraryInstance.getBooks().getAt(params.id2)  as JSON
                                }


                                break;

                            case "application/xml":
                                def book=libraryInstance.getBooks().getAt(params.id2)
                                if(!book)
                                {
                                    render(status:404,text:"Livre non trouvée")
                                }
                                else {
                                    render  libraryInstance.getBooks().getAt(params.id2)  as XML
                                }
                                break;
                            default:

                                render( status: 405, text:"Format non supporté")

                                break;
                        }


                    }
                }

                break;
            case "PUT":

               if(!params.id2)
               {
                   return
               }
                else {


                       if(!Book.findById(params.id2))
                       {
                           render(status: 404,text: "Livre non trouvée")
                       }
                       def bookInstance =Book.findById(params.id2)

                       bookInstance.setName(params.name)
                       bookInstance.setReleaseDate(Date.parse("dd-MM-yyyy", params.releaseDate))
                       bookInstance.setIsbn(params.isbn);
                       bookInstance.setAuthor(params.author)
                       bookInstance.setLibrary(Library.findById(params.library.id))

                       if(bookInstance.save(flush:true))
                       {
                           render(status: 200,text: "Livre modifié avec succès")
                       }
                       else  render(status: 404,text: "Echec de modification du livre")


               }

                break;
            case "DELETE":


                if(!params.id2)
            {
                return
            }
            else {
               if(!params.id1)
               {
                   if(!Book.findById(params.id2))
                   {
                       render(status: 404,text: "Livre non trouvée")
                   }
                   def bookInstance =Book.findById(params.id2)

                   if(!bookInstance)
                   {
                       response.status=404
                   }else
                   {
                       bookInstance.delete(flush:true)
                       response.status=200
                   }

               }
               else
               {
                   def libraryInstance= Library.findById(params.id1)

                   if(!libraryInstance.getBooks().getAt(params.id2))
                   {
                       render(status: 404,text: "Livre non trouvée")
                   }
                   def bookInstance =libraryInstance.getBooks().getAt(params.id2)

                   if(!bookInstance)
                   {
                       response.status=404
                   }else
                   {
                       bookInstance.delete(flush:true)
                       response.status=200
                   }

               }


            }
                break;
            default:
                response.status= 405
                break;
        }

    }

    def Library ()
    {
        switch(request.getMethod())
        {
            case "POST":

                def libraryInstance=new Library(params)
                if(libraryInstance.save(flush:true))
                {
                    response.status=201
                }
                else response.status=400
                break;
            case "GET":

                if(!params.id)
                {



                    switch (request.getHeader("Accept")) {
                        case "application/json":
                            render Library.findAll()  as JSON
                            break;

                        case "application/xml":
                            render Library.findAll() as XML
                            break;
                        default:

                            render( status: 405, text:"Format non supporté")

                            break;
                    }

                }
                else
                {
                    if( !Library.findById(params.id) ) response.status=404
                    else
                    {

                        switch (request.getHeader("Accept")) {
                            case "application/json":
                                render  Library.findById(params.id)  as JSON
                                break;

                            case "application/xml":
                                render  Library.findById(params.id) as XML
                                break;
                            default:

                                render( status: 405, text:"Format non supporté")

                                break;
                        }



                    }
                }

                break;
            case "PUT":
                if (params== null) {

                    return
                }
                if(!Library.findById(params.id))
                {
                    render(status: 404,text: "element non trouvé")
                }
                def libraryInstance =Library.findById(params.id)

                libraryInstance.setName(params.name)
                libraryInstance.setAddress(params.address)
                libraryInstance.setYearCreated(Date.parse("yyyy", params.yearCreated).year)
                if(libraryInstance.save(flush:true))
                {
                    render(status: 200,text: "Librairie modifié avec succès")
                }
                else  render(status: 404,text: "Echec de modification de la librairie")


                break;
            case "DELETE":

                if(!params.id)
                {
                    reponse.status=404
                }
                def library=Library.findById(params.id)
                if(!library)
                {
                    response.status=404
                }else
                {
                    library.delete(flush:true)
                    response.status=200
                }

                break;
                default:
                response.status= 405
                break;
        }

    }
}
