def read_html(html_file_name):
    html_file = open(html_file_name, "r")
    if html_file.mode == "r":
        html_page_content = html_file.read()

    return html_page_content


#This functions finds the index of every href in the webpage
def find_indices_href(html_page_content):
    indices_href = []
    start = 0
    while True:
        index = html_page_content.find("href=", start)
        
        if index == -1:
            break
        
        indices_href.append(index)
        start = index+1
    
    return indices_href


#This function gets the html page link from the the tag containing href.
#Input to this function is the index of href=
def get_html_href(href_index, html_page_content):
    href_end = html_page_content.find(">", href_index)
    html_file_name = html_page_content[href_index+len("href="):href_end].replace('"', '')

    return html_file_name


## Get every link in the html page
#Gets the index of every href
#Get the html on every href
#Returns all th links found on the page
def process_html(html_page_content):
    indices_href = find_indices_href(html_page_content)
    connected_html_files = []
    for index_href in indices_href:
        #get the html file name
        connected_html_files.append(get_html_href(index_href, html_page_content))
    return connected_html_files


#Recursivley crawls the connected links
#This function ends when every page is visited
def crawler(source_file):
    if source_file in results_dict.keys():   #Base case
        return

    html_page_content = read_html(source_file)
    results_dict[source_file] = process_html(html_page_content) #Memoization

    for target in results_dict[source_file]:
        crawler(target)


#Write the results in the CSV
def write_csv(results_dictionary, filename = "results.csv"):
    with open(filename, 'w') as f:
        for source in results_dict.keys():
            targets = results_dict[source]
            targets = ",".join(targets)
            f.write("%s,%s\n"%(source,targets))


results_dict = {}
source_file = input("Enter source file: ")
crawler(source_file)
write_csv(results_dict)
for item in results_dict.items():
    print(item)