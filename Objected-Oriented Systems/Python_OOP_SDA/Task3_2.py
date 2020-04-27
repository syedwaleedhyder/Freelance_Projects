#!/usr/bin/python3

import tkinter as tk
from random import randint



class App(tk.Frame):
    def __init__(self, master=None):
        tk.Frame.__init__(self, master)
        self.pack()

        ########
        self.random_value = randint(0,100)
        self.current_turn = 1
        self.entered_value1 = 0
        self.entered_value2 = 0
        self.score1=0
        self.score2=0
        ########

        self.top = tk.Tk()

        self.top.title("2-player Hi-Lo Game")
        self.top.geometry("500x300")
        self.top.resizable(0, 0)

        self.frame = tk.Frame(self.top)

        self.status_textbox = tk.Text(self.frame, height=10, width=60 )
        self.scrollbar_text = tk.Scrollbar(self.frame)

        self.scrollbar_text.config(command=self.status_textbox.yview)
        self.status_textbox['yscrollcommand'] = self.scrollbar_text.set
        # status_textbox.config(yscrollcommand=scrollbar_text.set)
        self.player1_label = tk.Label(self.top, text="Player 1: ")
        self.player1_entry = tk.Entry(self.top)
        self.player2_label = tk.Label(self.top, text="Player 2: ")
        self.player2_entry = tk.Entry(self.top, state="disabled")

        self.submit_button = tk.Button(self.top, text ="Submit", command = self.submit)
        self.play_again_button = tk.Button(self.top, text ="Play Again", command = self.play_again, state="disabled")

        self.player1_label.place(x=10, y=10)
        self.player1_entry.place(x=70, y=10)
        self.player2_label.place(x=10, y=50)
        self.player2_entry.place(x=70, y=50)
        self.submit_button.place(x=30, y=80)
        self.play_again_button.place(x=110, y=80)
        self.frame.place(x=10, y=120)
        self.status_textbox.pack(side="left")
        self.scrollbar_text.pack(side="right", fill="y")
        # scrollbar_text.place(x=100, y=120)

    def submit(self):
        if(self.current_turn == 1):
            self.entered_value1 = int(self.player1_entry.get());
            if(self.entered_value1 < self.random_value):
                self.status_textbox.insert("end", "\nProcessing player 1: "+ str(self.entered_value1) + ", Result = Too Low Score: " + str(self.score1))
            elif(self.entered_value1 > self.random_value):
                self.status_textbox.insert("end", "\nProcessing player 1: "+ str(self.entered_value1) + ", Result = Too High Score: " + str(self.score1))
            else:
                self.score1+=2
                self.status_textbox.insert("end", "\nProcessing player 1: "+ str(self.entered_value1) + ", Result = Correct Score: " + str(self.score1))
                self.if_correct()
                return
            self.player1_entry.config(state="disabled")
            self.player2_entry.config(state="normal")
            self.current_turn = 2
        elif(self.current_turn == 2):
            self.entered_value2 = int(self.player2_entry.get());
            if(self.entered_value2 < self.random_value):
                if(abs(self.random_value - self.entered_value1) < abs(self.random_value-self.entered_value2)):
                    self.score1+=1
                elif(abs(self.random_value - self.entered_value1) > abs(self.random_value-self.entered_value2)):
                    self.score2+=1
                self.status_textbox.insert("end", "\nProcessing player 2: "+ str(self.entered_value2) + ", Result = Too Low Score: " +str(self.score2) )
            elif(self.entered_value2 > self.random_value):
                if(abs(self.random_value - self.entered_value1) < abs(self.random_value-self.entered_value2)):
                    self.score1+=1
                elif(abs(self.random_value - self.entered_value1) > abs(self.random_value-self.entered_value2)):
                    self.score2+=1
                self.status_textbox.insert("end", "\nProcessing player 2: "+ str(self.entered_value2) + ", Result = Too High Score: " + str(self.score2))
            else:
                self.score2+=2
                self.status_textbox.insert("end", "\nProcessing player 2: "+ str(self.entered_value2) + ", Result = Correct Score: " + str(self.score2))
                self.if_correct()
                return
            self.current_turn = 1
        
            self.player1_entry.config(state="normal")
            self.player2_entry.config(state="disabled")

    def if_correct(self):
        self.submit_button.config(state="disabled")
        self.play_again_button.config(state="normal")

        
    def play_again(self):
        self.submit_button.config(state="normal")
        self.play_again_button.config(state="disabled")
        self.player1_entry.config(state="normal")
        self.player2_entry.config(state="disabled")
        self.random_value = randint(0,99)
        self.entered_value1 = 0
        self.entered_value2 = 0
        self.current_turn = 1

if __name__ == "__main__":
    app = App()
    app.mainloop()
# top.mainloop()