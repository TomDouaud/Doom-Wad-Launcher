# Import Tkinter 
from tkinter import *
from tktooltip import ToolTip

# Create a root window
root = Tk()

# Root window title and dimension
root.title("DOOM Wad Launcher")
# Set geometry (widthxheight)
root.geometry('1000x600')
# Block resizing of the window
root.resizable(False, False)
# Set background color
root.config(bg='#333333')

# Create the top navigation menu
menu = Frame(root, bg='#514C4C')
# Place the menu at the top (north) of the root window
menu.place(x=0, y=0, width=1000, height=60)

def changeSourcePort(*args):
    print("Source port selected " + args[0])
    match args[0]:
        case "GZDoom":
            sourcePortButton.config(image=GZ_icon)
        case "Zandronum":
            sourcePortButton.config(image=ZAND_icon)
        case "Chocolate Doom":
            sourcePortButton.config(image=CHOC_icon)
        case "WIP":
            sourcePortButton.config(image=WIP)
    # TODO apply the changes to the config file after finishing the GUI

# Create a dropdown button for choosing the DooM source port
# Default value shown is "Choose a source port"
sourcePortButton = OptionMenu(menu, StringVar(menu, "Choose a source port"), 
                                              "GZDoom", "Zandronum", 
                                              "Chocolate Doom", "WIP", 
                                              command=changeSourcePort)


# Show the default value
iconSelected = PhotoImage(file="./img/Default.png")
sourcePortButton.config(bg='#514C4C', fg='#FFFFFF', font=('Arial', 12, 'bold'),
                        activebackground='#514C4C', activeforeground='#FFFFFF', # Color of the text and the box when the mouse hoover 
                        highlightthickness=0, indicatoron=0, width=200, 
                        image=iconSelected, compound="left")

# Add icons to the dropdown menu
GZ_icon = PhotoImage(file="./img/GZ_icon.png")
sourcePortButton["menu"].entryconfigure(0, image=GZ_icon, compound="left")

ZAND_icon = PhotoImage(file="./img/ZAND_icon.png")
sourcePortButton["menu"].entryconfigure(1, image=ZAND_icon, compound="left")

CHOC_icon = PhotoImage(file="./img/CHOC_icon.png")
sourcePortButton["menu"].entryconfigure(2, image=CHOC_icon, compound="left")

WIP = PhotoImage(file="./img/WIP_icon.png")
sourcePortButton["menu"].entryconfigure(3, image=WIP, compound="left")

sourcePortButton.pack(side=LEFT, padx=10, pady=10)

# Add a button to edit the parameters of the application
settingsIcon = PhotoImage(file="./img/settings.png")
parametersButton = Button(menu, image=settingsIcon, bg='#514C4C', fg='#FFFFFF', 
                          font=('Arial', 12, 'bold'))
parametersButton.config(activebackground='#514C4C', activeforeground='#FFFFFF')

parametersButton.pack(side=LEFT, padx=10, pady=10)

ToolTip(parametersButton, "Edit the settings of the application", delay=0, 
        parent_kwargs={"bg": "#514C4C",},
        fg="#ffffff", bg="#514C4C")


# Add a refresh button to reload the wads
refreshIcon = PhotoImage(file="./img/refresh.png")
refreshButton = Button(menu, image=refreshIcon, bg='#514C4C', fg='#FFFFFF',
                       font=('Arial', 12, 'bold'))
refreshButton.config(activebackground='#514C4C', activeforeground='#FFFFFF') 

refreshButton.pack(side=RIGHT, padx=10, pady=10)

ToolTip(refreshButton, "Refresh the wads list", delay=0, 
        parent_kwargs={"bg": "#514C4C",},
        fg="#ffffff", bg="#514C4C")

# Add a button to launch Doomseeker
doomseekerIcon = PhotoImage(file="./img/DMSK_icon.png")
doomseekerButton = Button(menu, image=doomseekerIcon, bg='#514C4C', fg='#FFFFFF',
                       font=('Arial', 12, 'bold'))
doomseekerButton.config(activebackground='#514C4C', activeforeground='#FFFFFF') 

doomseekerButton.pack(side=RIGHT, padx=10, pady=10)

ToolTip(doomseekerButton, "Launch Doomseeker", delay=0, 
        parent_kwargs={"bg": "#514C4C",},
        fg="#ffffff", bg="#514C4C")

# Add a button to launch Doom Explorer
doomExplorerIcon = PhotoImage(file="./img/DEXP_icon.png")
doomExplorerButton = Button(menu, image=doomExplorerIcon, bg='#514C4C', 
                            fg='#FFFFFF',font=('Arial', 12, 'bold'))
doomExplorerButton.config(activebackground='#514C4C', activeforeground='#FFFFFF') 

doomExplorerButton.pack(side=RIGHT, padx=10, pady=10)

ToolTip(doomExplorerButton, "Launch Doom Explorer", delay=0, 
        parent_kwargs={"bg": "#514C4C",},
        fg="#ffffff", bg="#514C4C")

# Add a button to launch Odamex
odamexIcon = PhotoImage(file="./img/ODA_icon.png")
odamexButton = Button(menu, image=odamexIcon, bg='#514C4C', fg='#FFFFFF',
                      font=('Arial', 12, 'bold'))
odamexButton.config(activebackground='#514C4C', activeforeground='#FFFFFF')

odamexButton.pack(side=RIGHT, padx=10, pady=10)

ToolTip(odamexButton, "Launch Odamex", delay=0, 
        parent_kwargs={"bg": "#514C4C",},
        fg="#ffffff", bg="#514C4C")

# Add a button to launch Zdaemon
zdaemonIcon = PhotoImage(file="./img/ZD_icon.png")
zdaemonButton = Button(menu, image=zdaemonIcon, bg='#514C4C', fg='#FFFFFF',
                       font=('Arial', 12, 'bold'))
zdaemonButton.config(activebackground='#514C4C', activeforeground='#FFFFFF') 

zdaemonButton.pack(side=RIGHT, padx=10, pady=10)

ToolTip(zdaemonButton, "Launch ZDaemon", delay=0, 
        parent_kwargs={"bg": "#514C4C",},
        fg="#ffffff", bg="#514C4C")

# Add a search bar to filter the wads
searchbar = Entry(root, width=30, font=('Arial', 16), fg='#FFFFFF', 
                  bg='#514C4C', highlightthickness=0, insertbackground='#FFFFFF')
searchbar.insert(0, "Search for a wad...")
searchbar.config(insertwidth=0, insertofftime=1000)

# Add a function to delete the placeholder text when the searchbar is selected
def on_entry_select(event):
    if searchbar.get() == "Search for a wad...":
        searchbar.delete(0, END)

searchbar.bind("<FocusIn>", on_entry_select)

searchbar.place(x=50, y=80, width=300, height=40)

# TODO when selecting any button, the searchbar shouldn't be selected anymore
# TODO when deleting everything on the toolbar, the placeholder should be shown again
# TODO get the value of the searchbar every time it's content changes

# Add a button to filter the wads with tags
tagButton = Button(root, text="Tags", bg='#514C4C', fg='#FFFFFF', 
                   font=('Arial', 12, 'bold'))
tagButton.config(activebackground='#514C4C', activeforeground='#FFFFFF')
tagButton.place(x=370, y=80, width=100, height=40)

ToolTip(tagButton, "Add tags options \n(played, date, multiplayer...)", 
        delay=0, 
        parent_kwargs={"bg": "#514C4C",},
        fg="#ffffff", bg="#514C4C")

# Add a button to clear the tags
clearTagicon = PhotoImage(file="./img/clear.png")
clearTagButton = Button(root, image=clearTagicon, bg='#514C4C', fg='#FFFFFF')
clearTagButton.config(activebackground='#514C4C', activeforeground='#FFFFFF') 
clearTagButton.place(x=490, y=80, height=40)

ToolTip(clearTagButton, "Clear all the current tags", delay=0, 
        parent_kwargs={"bg": "#514C4C",},
        fg="#ffffff", bg="#514C4C")

# Add a function to change the icon of the button when clicked
sort_order = "asc"
def changeSortWadButton():
    global sort_order
    if sort_order == "asc":
        sortWadButton.config(image=sortWadicon2)
        sort_order = "desc"
    else:
        sortWadButton.config(image=sortWadicon)
        sort_order = "asc"

# Add a button to sort the wads
sortWadicon = PhotoImage(file="./img/asc.png")
sortWadicon2 = PhotoImage(file="./img/desc.png")
sortWadButton = Button(root, image=sortWadicon, bg='#514C4C', fg='#FFFFFF', command=changeSortWadButton)
sortWadButton.config(activebackground='#514C4C', activeforeground='#FFFFFF') 
sortWadButton.place(x=550, y=80, height=40)

ToolTip(sortWadButton, "Sort the wad by name ascending or descending ", delay=0, 
        parent_kwargs={"bg": "#514C4C",},
        fg="#ffffff", bg="#514C4C")


# Add a canvas to display the wads with a scrollbar
wadList = Canvas(root)
wadList.place(x=50, y=140, width=550, height=400)
# Add style to the canvas
wadList.config(bg='#5B5B5B', highlightthickness=0)

scrollbar = Scrollbar(wadList, command=wadList.yview)
scrollbar.pack(side=RIGHT, fill=Y)

# Configure the canvas to use the scrollbar
wadList.config(yscrollcommand=scrollbar.set)

# TODO create and add the object wadDisplay to the frame wadFrame

# Add 100 labels to the canvas for placeholder
wadFrame = Frame(wadList, bg='#5B5B5B')
for i in range(100):
    label = Label(wadFrame, text="Placeholder text %s" % i, bg='#5B5B5B', 
                  fg='#FFFFFF', font=('Arial', 12))
    label.pack()

# Add the frame to the canvas
wadList.create_window((0, 0), window=wadFrame, anchor="nw")

# Configure the canvas to update the scrollregion
wadFrame.bind("<Configure>", lambda e: wadList.config(scrollregion=wadList.bbox("all")))

# Add the possibility to scroll with the mousewheel
def on_mousewheel(event):
    wadList.yview_scroll(int(-1*(event.delta/120)), "units")

wadList.bind("<MouseWheel>", on_mousewheel)

# Add a Canvas to display the wad information
wadInfo = Canvas(root, bg='#968B00', highlightthickness=0)
wadInfo.place(x=620, y=80, width=330, height=460)

# Add a carousel to display the screenshots of the wad
carousel = Canvas(wadInfo, bg='#008B8B', highlightthickness=0)
carousel.place(x=55, y=30, width=220, height=165)

# Add a frame to display the wad information
wadInfoText = Canvas(wadInfo, bg='#968B8B', highlightbackground="#968B8B")
wadInfoText.place(x=55, y=220, width=220, height=165)

# Add a scrollbar to the wadInfoText
scrollbarInfo = Scrollbar(wadInfoText, command=wadInfoText.yview)
scrollbarInfo.pack(side=RIGHT, fill=Y)

# Configure the canvas to use the scrollbar
wadInfoText.config(yscrollcommand=scrollbar.set)

# Add a description label
descriptionLabel = Label(wadInfoText, bg='#968B8B', 
                         fg='#FFFFFF', font=('Arial', 12))
# Add the test
descriptionLabel.config(text="Title\nAuthor\nRelease \nDate : 2\n0\n05\nCoo\n\n\n\np : Yes\nDeathmatch : Yes\nIWAD : Doom 2\nRating : 4.5/5\nYour rating :")
descriptionLabel.config(justify=LEFT)
descriptionLabel.place(x=0, y=0)

# Configure the canvas to update the scrollregion
wadInfoText.bind("<Configure>", lambda e: wadInfoText.config(scrollregion=wadInfoText.bbox("all")))

# Add the possibility to scroll with the mousewheel
def on_mousewheel(event):
    print("scroll") # FIXME doesn't work
    wadInfoText.yview_scroll(int(-1*(event.delta/120)), "units")

wadInfoText.bind("<MouseWheel>", on_mousewheel)


# Execute Tkinter
root.mainloop()