import json
import os
import re

log_path = "/home/eya/.gemini/antigravity/brain/69f91692-3b60-4230-b982-91cd435832c5/.system_generated/logs/overview.txt"
dest_root = "/home/eya/Eya2eme/projet_stage"

print(f"Reading logs from {log_path}...")
with open(log_path, "r", encoding="utf-8") as f:
    lines = f.readlines()

count = 0
for line_num, line in enumerate(lines):
    try:
        data = json.loads(line.strip())
        # Check if this step has tool calls
        if "tool_calls" in data:
            for tc in data["tool_calls"]:
                if tc.get("name") == "write_to_file":
                    args = tc.get("args", {})
                    target_file = args.get("TargetFile", "").strip('"')
                    code_content = args.get("CodeContent", "")
                    
                    if target_file and code_content:
                        # Normalize target file path
                        if not target_file.startswith("/"):
                            target_file = os.path.join(dest_root, target_file)
                        
                        # Replace escaped newlines if it's a raw string
                        if code_content.startswith('"') and code_content.endswith('"'):
                            try:
                                code_content = json.loads(code_content)
                            except:
                                code_content = code_content[1:-1].replace('\\n', '\n').replace('\\t', '\t').replace('\\"', '"')
                        
                        # Create parent directories if they don't exist
                        os.makedirs(os.path.dirname(target_file), exist_ok=True)
                        
                        # Write the file
                        with open(target_file, "w", encoding="utf-8") as out:
                            out.write(code_content)
                        print(f"Extracted: {target_file}")
                        count += 1
    except Exception as e:
        pass

print(f"Done! Extracted {count} files successfully.")
