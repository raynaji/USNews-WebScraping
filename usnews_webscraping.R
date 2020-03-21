
# install.packages("rvest")
library(rvest)
# Load the dplyr package so that we can use the pipe operator
library(dplyr)
library(stringr)


# Go to https://www.usnews.com/
scraping <- read_html("https://www.usnews.com")


# Find its current "Top Stories":
#   Firstly, we use the node of <h3> to identify the story chunks.
#   In that chunk, we further identify the node of <a href="...."> to exact the href attribute of that story.
#     html_nodes(xpath) leads to the node with @ identifying certain attributes.
#     html_attr returns the attribute in that node.
#     html_text returns the text in that node.
url <- scraping %>%
  html_nodes("h3") %>%
  html_nodes("a") %>%
  html_attr("href")

# Read + print the URL of the _second_ current top story to the screen:
topstory <- read_html(url[2])
url[2]

# Read + print the header of the second story:
#   We use the node of <h1> to identify the title nodes.
#   Then html_text returns the title text.
header <- topstory %>%
  html_nodes("h1") %>%
  html_text()
header


# Read and print the first 3 sentences of the main body to the screen:

# In the new 2nd story url, we use the xpath and class to identify the body text.
# Then html_text returns the body text.
bodytext <- topstory %>% 
  html_nodes(xpath = "//div[@class='Raw-s14xcvr1-0 AXWJq']") %>%
  html_text()

sentences <- bodytext %>%
  strsplit("\\. ") # Here we use ._ to split in case the dot occuring in some names. But the limitations are this does not detect ? !
# This method only returns the paragraphs with main body text. And the result is split by sentences in paragraphs.

# This is to unlist the sentences. Originally, there are two layers, first is the paragraph and the second is the sentences in that paragraph.
# By unlisting, we get the second layer of sentences. Then we can get the first three items to get the first three sentences.
sentences <- unlist(sentences)
firstthree <- sentences[1:3]
firstthree

