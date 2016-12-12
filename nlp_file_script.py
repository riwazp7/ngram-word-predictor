'''
Python Script for NLP final project
Basically we only wants lines to end with periods.
'''

with open('bio_of_rabbit.txt', 'r') as rf:
    with open('fixed.txt', 'w') as wf:

        for line in rf:

            if line.endswith(".\n"):
                wf.write(line)

            elif line.endswith("?\n"):
                line = line.rstrip("?\n")
                line = line + ".\n"
                wf.write(line)

            elif line.endswith("!\n"):
                line = line.rstrip("!\n")
                line = line + ".\n"
                wf.write(line)

            elif line.endswith(",\n"):
                line = line.rstrip("\n")
                wf.write(line+" ")

            elif len(line) == 1:
                wf.write(line.rstrip("\n"))

            elif line.endswith("\n"):
                wf.write(line.rstrip("\n") + " ")

            
            else:
                #line = line.rstrip(
                line = line.rstrip("\n")
                line = line + ".\n"
                wf.write(line)
