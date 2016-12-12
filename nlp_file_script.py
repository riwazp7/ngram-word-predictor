'''
Python Script for NLP final project
Basically we only wants lines to end with periods,
and to ignore punctuation that can alter NGram probabilities.
'''

with open('bio_of_rabbit.txt', 'r') as rf:
    with open('fixed.txt', 'w') as wf:

        for line in rf:

            line = line.replace("(","");
            line = line.replace(")","");
            line = line.replace('"',"");
            line = line.replace(",","");
            line = line.replace("?",".");
            line = line.replace("!",".");
            line = line.replace(";","");
            line = line.replace("[","");
            line = line.replace("]","");
            line = line.replace("{","");
            line = line.replace("}","");
            line = line.replace("_","");
            line = line.replace("<","");
            line = line.replace(">","");
            line = line.replace("\\","");
            line = line.replace("\\/","");
            line = line.replace("~","");

            if line.endswith(".\n"):
                wf.write(line)

            elif len(line) == 1:
                wf.write(line.rstrip("\n"))

            elif line.endswith("\n"):
                wf.write(line.rstrip("\n") + " ")

            else:
                line = line.rstrip("\n")
                line = line + ".\n"
                wf.write(line)
