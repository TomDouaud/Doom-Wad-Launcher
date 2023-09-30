import tkinter as Tk
from tkinter import RAISED, LEFT


class ui:
    def __init__(self, root):

        # Root window title and dimension
        root.title("SDL A Simple DooM Launcher")
        # Set geometry (widthxheight)
        root.geometry('1000x600')
        # Block resizing of the window
        root.resizable(False, False)
        # Set background color
        root.config(bg='#333333')

        # Create the top navigation menu
        menu = Tk.Frame(root, bg='#514C4C')
        # Place the menu at the top (north) of the root window
        menu.place(x=0, y=0, width=1000, height=60)

        # Create a dropdown button for choosing the DooM source port
        # Default value shown is "Choose a source port"
        sourcePort = Tk.OptionMenu(menu, Tk.StringVar(menu, "Choose a source port"), "GZDoom", "Zandronum", "Chocolate Doom",
                                "WIP")

        # Show the default value
        iconSelected = Tk.PhotoImage(file="img/Default.png")
        sourcePort.config(bg='#514C4C', fg='#FFFFFF', font=('Arial', 12, 'bold'),
                          activebackground='#514C4C', activeforeground='#FFFFFF',
                          relief=RAISED, highlightthickness=0, indicatoron=0, width=200, image=iconSelected,
                          compound="left")

        # Add icons to the dropdown menu
        icon1 = Tk.PhotoImage(file="img/icon1.png")
        sourcePort["menu"].entryconfigure(0, image=icon1, compound="left")

        icon2 = Tk.PhotoImage(file="img/icon2.png")
        sourcePort["menu"].entryconfigure(1, image=icon2, compound="left")

        icon3 = Tk.PhotoImage(file="img/icon3.png")
        sourcePort["menu"].entryconfigure(2, image=icon3, compound="left")

        icon4 = Tk.PhotoImage(file="img/icon4.png")
        sourcePort["menu"].entryconfigure(3, image=icon4, compound="left")

        # TODO change the icon to the source port selected

        sourcePort.pack(side=LEFT, padx=10, pady=10)
