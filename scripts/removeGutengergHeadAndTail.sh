for file in "$@"
do
    START=$(grep -n -m1 -i ".*start.*GUTENBERG" $file | cut -d: -f1)
    END=$(grep -n -m1 -i ".*end.*GUTENBERG" $file | cut -d: -f1)
    START=$((START+1))
    END=$((END-1))
    echo "$START $END"
    TEXT="$START,$END""!d"
    sed -i "$TEXT" $file
done

